package nl.ploentuin.ploentuin.controller;

import jakarta.validation.Valid;
import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.dto.info.InfoCategoryDto;
import nl.ploentuin.ploentuin.service.InfoPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info/categories")
public class InfoCategoryController {

    private final InfoPageService infoPageService;

    public InfoCategoryController(InfoPageService infoPageService) {
        this.infoPageService = infoPageService;
    }

    @GetMapping
    public List<InfoCategoryDto> getAllCategories() {
        return infoPageService.getAllCategories();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody InfoCategoryDto dto) {
        try {
            infoPageService.createCategory(dto);
            return ResponseHelper.created("Categorie succesvol aangemaakt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable int id,
            @Valid @RequestBody InfoCategoryDto dto) {
        try {
            infoPageService.updateCategory(id, dto);
            return ResponseHelper.ok(null, "Categorie succesvol bijgewerkt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            infoPageService.deleteCategory(id);
            return ResponseHelper.ok(null, "Categorie succesvol verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }
}