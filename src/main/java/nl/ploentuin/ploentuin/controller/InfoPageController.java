package nl.ploentuin.ploentuin.controller;

import jakarta.validation.Valid;
import nl.ploentuin.ploentuin.dto.api.ApiResponse;
import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.dto.info.*;
import nl.ploentuin.ploentuin.service.InfoPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info/pages")
public class InfoPageController {

    private final InfoPageService infoPageService;

    public InfoPageController(InfoPageService infoPageService) {
        this.infoPageService = infoPageService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InfoPageInfoDto>>> getAllPages() {
        List<InfoPageInfoDto> pages = infoPageService.getAllPages();
        return ResponseHelper.ok(pages, "Pagina’s opgehaald");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InfoPageInfoDto>> getPageById(@PathVariable int id) {
        try {
            InfoPageInfoDto page = infoPageService.getPageById(id);
            return ResponseHelper.ok(page, "Pagina opgehaald");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<InfoPageMinimalDto>>> getPagesByCategory(
            @PathVariable int categoryId) {
        try {
            List<InfoPageMinimalDto> pages = infoPageService.getPagesByCategory(categoryId);
            return ResponseHelper.ok(pages, "Pagina’s per categorie opgehaald");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @GetMapping("/grouped")
    public ResponseEntity<ApiResponse<List<InfoPageFilterDto>>> getGroupedPages() {
        List<InfoPageFilterDto> grouped = infoPageService.getPagesGroupedByCategory();
        return ResponseHelper.ok(grouped, "Pagina’s gegroepeerd per categorie");
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse<List<InfoPageMinimalDto>>> searchByTitle(@RequestParam String q) {
        List<InfoPageMinimalDto> result = infoPageService.searchPagesByTitle(q);
        return ResponseHelper.ok(result, "Zoekresultaten op titel");
    }

    @GetMapping("/search/content")
    public ResponseEntity<ApiResponse<List<InfoPageMinimalDto>>> searchByContent(@RequestParam String q) {
        List<InfoPageMinimalDto> result = infoPageService.searchPagesByContent(q);
        return ResponseHelper.ok(result, "Zoekresultaten op inhoud");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Void>> createPage(@Valid @ModelAttribute InfoPageCreateDto dto) {
        try {
            infoPageService.createPage(dto);
            return ResponseHelper.created("Pagina aangemaakt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Void>> updatePage(@PathVariable int id,
                                                        @ModelAttribute InfoPageUpdateDto dto) {
        try {
            infoPageService.updatePage(id, dto);
            return ResponseHelper.ok(null, "Pagina bijgewerkt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePage(@PathVariable int id) {
        try {
            infoPageService.deletePage(id);
            return ResponseHelper.ok(null, "Pagina verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }
}