package nl.ploentuin.ploentuin.controller;

import nl.ploentuin.ploentuin.dto.planner.*;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.service.PlannerItemCatalogService;
import nl.ploentuin.ploentuin.service.PlannerService;
import org.springframework.http.HttpStatus;
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
    public void deletePlanner(@PathVariable int plannerId) {
        plannerService.deletePlanner(plannerId);
    }

    @PostMapping("/{plannerId}/place")
    public PlannerItemPlacementDto placeItem(@PathVariable int plannerId,
                                             @RequestParam int catalogItemId,
                                             @RequestParam int row,
                                             @RequestParam int column) {
        return plannerService.placeItem(plannerId, catalogItemId, row, column);
    }

    @DeleteMapping("/{plannerId}/items/{placementId}")
    public void deletePlacement(@PathVariable int plannerId,
                                @PathVariable int placementId,
                                Authentication auth) {
        User user = getCurrentUser(auth);
        PlannerInfoDto planner = plannerService.getPlanner(plannerId);
        if (user != null && planner.getUserId() != user.getId()) {
            throw new IllegalArgumentException("Deze planner is niet van jouw!");
        }
        plannerService.removePlacement(placementId);
    }

    @GetMapping("/planner/catalog")
    public List<PlannerItemCatalogDto> getPlannerCatalog() {
        return plannerItemCatalogService.getAllItems();
    }

    @GetMapping("/planner/catalog?type=XYZ")
    public List<PlannerItemCatalogDto> getPlannerCatalogByType(
            @RequestParam PlannerItemCatalog.PlannerItemType type)
    {
        return plannerItemCatalogService.getItemsByType(type);
    }

    @GetMapping("/planner/catalog/{id}")
    public PlannerItemCatalogDto getPlannerCatalogById(
            @RequestParam int id)
    {
        return plannerItemCatalogService.getById(id);
    }

    @PostMapping("/catalog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createCatalogItem(
            @RequestBody PlannerItemCatalog item,
            Authentication auth) {

        User user = getCurrentUser(auth);
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Ge bent geen admin of wel?");
        }

        plannerItemCatalogService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Catalog item succesvol aangemaakt");
    }


    @DeleteMapping("/catalog")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCatalogItem(@RequestBody PlannerItemCatalogDto item, Authentication auth) {
        User user = getCurrentUser(auth);
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Ge bent geen admin of wel?");
        }

        plannerItemCatalogService.deleteItem(item.getId());
        return ResponseEntity.ok("Catalog item succesvol verwijderd");
    }



    private User getCurrentUser(Authentication auth) {
        if (auth == null) return null;
        return userRepository.findByUsernameIgnoreCase(auth.getName()).orElse(null);
    }
}
