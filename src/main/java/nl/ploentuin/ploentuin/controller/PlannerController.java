package nl.ploentuin.ploentuin.controller;

import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.dto.planner.*;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.service.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planner")
public class PlannerController {

    private final PlannerService plannerService;
    private final PlannerItemCatalogService plannerItemCatalogService;
    private final UserRepository userRepository;
    private final PlannerPngExportService plannerPngExportService;
    private final PlannerGridBuilderHelper gridBuilder;
    private final PlannerExportService plannerExportService;

    public PlannerController(PlannerService plannerService, PlannerItemCatalogService itemCatalogService,
                             UserRepository userRepository, PlannerPngExportService plannerPngExportService,
                             PlannerGridBuilderHelper gridBuilder, PlannerExportService plannerExportService) {
        this.plannerService = plannerService;
        this.plannerItemCatalogService = itemCatalogService;
        this.userRepository = userRepository;
        this.plannerPngExportService = plannerPngExportService;
        this.gridBuilder = gridBuilder;
        this.plannerExportService = plannerExportService;
    }

    @PostMapping
    public ResponseEntity<?> createPlanner(@RequestBody CreatePlannerDto dto, Authentication auth) {
        User user = getCurrentUser(auth);
        PlannerInfoDto planner = plannerService.createPlanner(dto, user);
        return ResponseHelper.ok(planner, "Planner succesvol aangemaakt");
    }

    @GetMapping("/{plannerId}")
    public ResponseEntity<?> getPlanner(@PathVariable int plannerId) {
        try {
            PlannerInfoDto planner = plannerService.getPlanner(plannerId);
            return ResponseHelper.ok(planner, "Planner gevonden");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PatchMapping("/{plannerId}")
    public ResponseEntity<?> updatePlanner(@PathVariable int plannerId, @RequestBody UpdatePlannerDto dto, Authentication auth) {
        User user = getCurrentUser(auth);
        try {
            PlannerInfoDto updated = plannerService.updatePlanner(plannerId, dto, user);
            return ResponseHelper.ok(updated, "Planner succesvol geüpdatet");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.forbidden(e.getMessage());
        }
    }

    @DeleteMapping("/{plannerId}")
    public ResponseEntity<?> deletePlanner(@PathVariable int plannerId, Authentication auth) {
        User user = getCurrentUser(auth);
        try {
            plannerService.deletePlanner(plannerId, user);
            return ResponseHelper.ok(null, "Planner succesvol verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PostMapping("/{plannerId}/place")
    public ResponseEntity<?> placeItem(@PathVariable int plannerId, @RequestParam int catalogItemId, @RequestParam int row, @RequestParam int column) {
        try {
            PlannerItemPlacementDto placement = plannerService.placeItem(plannerId, catalogItemId, row, column);
            return ResponseHelper.ok(placement, "Item geplaatst in planner");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @DeleteMapping("/{plannerId}/items/{placementId}")
    public ResponseEntity<?> deletePlacement(@PathVariable int plannerId, @PathVariable int placementId, Authentication auth) {
        User user = getCurrentUser(auth);
        PlannerInfoDto planner = plannerService.getPlanner(plannerId);
        if (planner.getUserId() != -1 && (user == null || planner.getUserId() != user.getId())) {
            return ResponseHelper.forbidden("Deze planner is niet van jou!");
        }
        try {
            plannerService.removePlacement(placementId);
            return ResponseHelper.ok(null, "Object verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @GetMapping("/catalog")
    public ResponseEntity<?> getPlannerCatalog() {
        return ResponseHelper.ok(plannerItemCatalogService.getAllItems(), "Catalog items opgehaald");
    }

    @GetMapping("/catalog/type")
    public ResponseEntity<?> getPlannerCatalogByType(@RequestParam PlannerItemCatalog.PlannerItemType type) {
        return ResponseHelper.ok(plannerItemCatalogService.getItemsByType(type), "Catalog items gefilterd op type");
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<?> getPlannerCatalogById(@PathVariable int id) {
        try {
            PlannerItemCatalogDto item = plannerItemCatalogService.getById(id);
            return ResponseHelper.ok(item, "Catalog item gevonden");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PostMapping("/catalog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCatalogItem(@RequestBody PlannerItemCatalog item, Authentication auth) {
        User user = getCurrentUser(auth);
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return ResponseHelper.forbidden("Je bent geen admin!");
        }
        plannerItemCatalogService.createItem(item);
        return ResponseHelper.created("Catalog item succesvol aangemaakt");
    }

    @DeleteMapping("/catalog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCatalogItem(@RequestBody PlannerItemCatalogDto item, Authentication auth) {
        User user = getCurrentUser(auth);
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return ResponseHelper.forbidden("Je bent geen admin!");
        }
        PlannerItemCatalogDto deletedItem = plannerItemCatalogService.getById(item.getId());
        plannerItemCatalogService.deleteItem(item.getId());
        return ResponseHelper.ok(deletedItem, "Catalog item succesvol verwijderd");
    }

    @GetMapping("/{plannerId}/export/{format}")
    public ResponseEntity<byte[]> exportPlanner(@PathVariable int plannerId, @PathVariable String format) {
        try {
            PlannerInfoDto planner = plannerService.getPlanner(plannerId);
            PlannerItemPlacementDto[][] grid = gridBuilder.buildGrid(planner);

            byte[] data;
            MediaType mediaType;
            String extension = format.toLowerCase();

            switch (extension) {
                case "png":
                    data = plannerPngExportService.export(plannerId);
                    mediaType = MediaType.IMAGE_PNG;
                    break;
                case "pdf":
                    data = plannerExportService.exportPdf(planner, grid, 60);
                    mediaType = MediaType.APPLICATION_PDF;
                    break;
                case "word":
                case "docx":
                    data = plannerExportService.exportWord(planner, grid, 60);
                    mediaType = MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                    extension = "docx";
                    break;
                case "excel":
                case "xlsx":
                    data = plannerExportService.exportExcel(planner, grid, 60);
                    mediaType = MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                    extension = "xlsx";
                    break;
                default:
                    return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"planner-" + plannerId + "." + extension + "\"")
                    .contentType(mediaType)
                    .body(data);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private User getCurrentUser(Authentication auth) {
        if (auth == null) return null;
        return userRepository.findByUsernameIgnoreCase(auth.getName()).orElse(null);
    }
}