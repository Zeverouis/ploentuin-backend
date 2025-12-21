package nl.ploentuin.ploentuin.controller;

import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.dto.planner.*;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.service.PlannerItemCatalogService;
import nl.ploentuin.ploentuin.service.PlannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planner")
public class PlannerController {

    private final PlannerService plannerService;
    private final PlannerItemCatalogService plannerItemCatalogService;
    private final UserRepository userRepository;

    public PlannerController(PlannerService plannerService, PlannerItemCatalogService itemCatalogService,
                             UserRepository userRepository) {
        this.plannerService = plannerService;
        this.plannerItemCatalogService = itemCatalogService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public PlannerInfoDto createPlanner(@RequestBody CreatePlannerDto dto,
                                        Authentication auth,
                                        @RequestParam(required = false) String anonymousToken) {
        User user = getCurrentUser(auth);
        return plannerService.createPlanner(dto, user, anonymousToken);
    }

    @GetMapping("/{plannerId}")
    public PlannerInfoDto getPlanner(@PathVariable int plannerId) {
        return plannerService.getPlanner(plannerId);
    }

    @GetMapping("/planner-anonymous/{token}")
    public PlannerInfoDto getPlannerAnonymous(@PathVariable String token) {
        return plannerService.getPlannerByAnonymousToken(token);
    }

    @PatchMapping("/{plannerId}")
    public PlannerInfoDto updatePlanner(@PathVariable int plannerId,
                                        @RequestBody UpdatePlannerDto dto,
                                        Authentication auth,
                                        @RequestParam(required = false) String anonymousToken) {
        User user = getCurrentUser(auth);
        return plannerService.updatePlanner(plannerId, dto, user, anonymousToken);
    }

    @DeleteMapping("/{plannerId}")
    public ResponseEntity<?> deletePlanner(@PathVariable int plannerId) {
        try {
            plannerService.deletePlanner(plannerId);
            return ResponseHelper.ok(null, "Planner succesvol verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PostMapping("/{plannerId}/place")
    public PlannerItemPlacementDto placeItem(@PathVariable int plannerId,
                                             @RequestParam int catalogItemId,
                                             @RequestParam int row,
                                             @RequestParam int column) {
        return plannerService.placeItem(plannerId, catalogItemId, row, column);
    }

    @DeleteMapping("/{plannerId}/items/{placementId}")
    public ResponseEntity<?> deletePlacement(@PathVariable int plannerId,
                                @PathVariable int placementId,
                                Authentication auth) {
        User user = getCurrentUser(auth);
        PlannerInfoDto planner = plannerService.getPlanner(plannerId);
        if (user != null && planner.getUserId() != user.getId()) {
            return ResponseHelper.forbidden("Deze planner is niet van jouw!");
        }
        plannerService.removePlacement(placementId);
        return ResponseHelper.ok(null, "Object verwijderd.");
    }

    @GetMapping("/planner/catalog")
    public List<PlannerItemCatalogDto> getPlannerCatalog() {
        return plannerItemCatalogService.getAllItems();
    }

    @GetMapping("/planner/catalog/type")
    public List<PlannerItemCatalogDto> getPlannerCatalogByType(
            @RequestParam PlannerItemCatalog.PlannerItemType type)
    {
        return plannerItemCatalogService.getItemsByType(type);
    }

    @GetMapping("/planner/catalog/{id}")
    public PlannerItemCatalogDto getPlannerCatalogById(
            @PathVariable int id)
    {
        return plannerItemCatalogService.getById(id);
    }

    @PostMapping("/catalog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCatalogItem(
            @RequestBody PlannerItemCatalog item,
            Authentication auth) {

        User user = getCurrentUser(auth);
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return ResponseHelper.forbidden("Ge bent geen admin of wel?");
        }

        plannerItemCatalogService.createItem(item);
        return ResponseHelper.created("Catalog item succesvol aangemaakt");
    }


    @DeleteMapping("/catalog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCatalogItem(@RequestBody PlannerItemCatalogDto item, Authentication auth) {
        User user = getCurrentUser(auth);
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return ResponseHelper.forbidden("Ge bent geen admin of wel?");
        }

        plannerItemCatalogService.deleteItem(item.getId());
        return ResponseHelper.ok(null, "Catalog item succesvol verwijderd");
    }


    private User getCurrentUser(Authentication auth) {
        if (auth == null) return null;
        return userRepository.findByUsernameIgnoreCase(auth.getName()).orElse(null);
    }
}
