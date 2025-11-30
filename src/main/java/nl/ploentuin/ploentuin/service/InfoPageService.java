package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.info.*;
import nl.ploentuin.ploentuin.model.InfoCategory;
import nl.ploentuin.ploentuin.model.InfoPage;
import nl.ploentuin.ploentuin.repository.InfoCategoryRepository;
import nl.ploentuin.ploentuin.repository.InfoPageRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InfoPageService {

    private final InfoPageRepository pageRepository;
    private final InfoCategoryRepository categoryRepository;

    public InfoPageService(InfoPageRepository pageRepository,
                           InfoCategoryRepository categoryRepository) {
        this.pageRepository = pageRepository;
        this.categoryRepository = categoryRepository;
    }

    private InfoCategoryDto toCategoryDto(InfoCategory category) {
        return new InfoCategoryDto(category.getId(), category.getCategoryName());
    }

    private InfoPageInfoDto toPageInfoDto(InfoPage page) {
        return new InfoPageInfoDto(
                page.getId(),
                page.getTitle(),
                page.getContent(),
                toCategoryDto(page.getInfoCategory())
        );
    }

    private InfoPageMinimalDto toMinimalDto(InfoPage page) {
        return new InfoPageMinimalDto(page.getId(), page.getTitle());
    }

    public List<InfoCategoryDto> getAllCategories() {
        return categoryRepository.findAllByOrderByCategoryNameAsc()
                .stream()
                .map(this::toCategoryDto)
                .collect(Collectors.toList());
    }

    public InfoCategory getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categorie niet gevonden"));
    }

    public InfoCategory createCategory(InfoCategoryDto dto) {
        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new IllegalArgumentException("Categorie met deze naam bestaat al");
        }
        InfoCategory category = new InfoCategory();
        category.setCategoryName(dto.getCategoryName());
        return categoryRepository.save(category);
    }

    public InfoCategory updateCategory(int id, InfoCategoryDto dto) {
        InfoCategory category = getCategoryById(id);
        if (!category.getCategoryName().equals(dto.getCategoryName())
                && categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new IllegalArgumentException("Categorie met deze naam bestaat al");
        }
        category.setCategoryName(dto.getCategoryName());
        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        InfoCategory category = getCategoryById(id);
        pageRepository.deleteAllByInfoCategory(category);
        categoryRepository.delete(category);
    }

    public List<InfoPageInfoDto> getAllPages() {
        return pageRepository.findAllByOrderByUpdatedAtDesc()
                .stream()
                .map(this::toPageInfoDto)
                .collect(Collectors.toList());
    }

    public InfoPageInfoDto getPageById(int id) {
        InfoPage page = pageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Page not found"));
        return toPageInfoDto(page);
    }

    public List<InfoPageMinimalDto> getPagesByCategory(int categoryId) {
        InfoCategory category = getCategoryById(categoryId);
        return pageRepository.findAllByInfoCategoryOrderByTitleAsc(category)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public InfoPageInfoDto createPage(InfoPageCreateDto dto) {
        InfoCategory category = getCategoryById(dto.getInfoCategoryId());

        if (pageRepository.existsByTitleAndInfoCategory(dto.getTitle(), category)) {
            throw new IllegalArgumentException("Pagina met deze naam bestaat al");
        }

        InfoPage page = new InfoPage();
        page.setTitle(dto.getTitle());
        page.setContent(dto.getContent());
        page.setInfoCategory(category);

        InfoPage saved = pageRepository.save(page);
        return toPageInfoDto(saved);
    }

    public InfoPageInfoDto updatePage(int pageId, InfoPageUpdateDto dto) {
        InfoPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new IllegalArgumentException("Pagina niet gevonden"));

        if (dto.getTitle() != null) page.setTitle(dto.getTitle());
        if (dto.getContent() != null) page.setContent(dto.getContent());

        InfoPage updated = pageRepository.save(page);
        return toPageInfoDto(updated);
    }

    public void deletePage(int pageId) {
        InfoPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new IllegalArgumentException("Pagina niet gevonden"));
        pageRepository.delete(page);
    }

    public List<InfoPageMinimalDto> searchPagesByTitle(String title) {
        return pageRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public List<InfoPageMinimalDto> searchPagesByContent(String content) {
        return pageRepository.findByContentContainingIgnoreCase(content)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public List<InfoPageFilterDto> getPagesGroupedByCategory() {
        List<InfoCategory> categories = categoryRepository.findAllByOrderByCategoryNameAsc();
        List<InfoPageFilterDto> result = new ArrayList<>();

        for (InfoCategory category : categories) {
            List<InfoPageMinimalDto> pages = pageRepository.findAllByInfoCategoryOrderByTitleAsc(category)
                    .stream()
                    .map(this::toMinimalDto)
                    .collect(Collectors.toList());
            result.add(new InfoPageFilterDto(category.getId(), category.getCategoryName(), pages));
        }

        return result;
    }
}