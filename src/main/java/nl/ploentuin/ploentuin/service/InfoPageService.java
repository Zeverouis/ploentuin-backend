package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.image.ImageCreateDto;
import nl.ploentuin.ploentuin.dto.image.ImageResponseDto;
import nl.ploentuin.ploentuin.dto.info.*;
import nl.ploentuin.ploentuin.model.Image;
import nl.ploentuin.ploentuin.model.InfoCategory;
import nl.ploentuin.ploentuin.model.InfoPage;
import nl.ploentuin.ploentuin.repository.InfoCategoryRepository;
import nl.ploentuin.ploentuin.repository.InfoPageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InfoPageService {

    private final InfoPageRepository pageRepository;
    private final InfoCategoryRepository categoryRepository;
    private final ImageService imageService;

    public InfoPageService(InfoPageRepository pageRepository,
                           InfoCategoryRepository categoryRepository,
                           ImageService imageService) {
        this.pageRepository = pageRepository;
        this.categoryRepository = categoryRepository;
        this.imageService = imageService;
    }

    private InfoCategoryDto toCategoryDto(InfoCategory category) {
        return new InfoCategoryDto(category.getId(), category.getCategoryName());
    }

    private InfoPageInfoDto toPageInfoDto(InfoPage page) {
        List<ImageResponseDto> images = imageService.getImagesByParent(page.getId(), Image.ParentType.INFOPAGE);

        return new InfoPageInfoDto(
                page.getId(),
                page.getTitle(),
                page.getTldr(),
                toCategoryDto(page.getInfoCategory()),

                page.getSectionOneTitle(),
                page.getSectionOneContent(),
                page.getSectionTwoTitle(),
                page.getSectionTwoContent(),
                page.getSectionThreeTitle(),
                page.getSectionThreeContent(),
                page.getSectionFourTitle(),
                page.getSectionFourContent(),

                images
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

    public List<InfoPageInfoDto> getLatestPages() {
        return pageRepository.findAllByOrderByUpdatedAtDesc()
                .stream()
                .limit(5) // Pak de 5 meest recent bijgewerkte pagina's
                .map(this::toPageInfoDto)
                .collect(Collectors.toList());
    }

    public List<InfoPageInfoDto> getAllPages() {
        return pageRepository.findAllByOrderByUpdatedAtDesc()
                .stream()
                .map(this::toPageInfoDto)
                .collect(Collectors.toList());
    }

    public InfoPageInfoDto getPageById(int id) {
        InfoPage page = pageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pagina niet gevonden"));
        return toPageInfoDto(page);
    }

    public InfoPageInfoDto createPage(InfoPageCreateDto dto) {
        InfoCategory category = getCategoryById(dto.getInfoCategoryId());

        if (pageRepository.existsByTitleAndInfoCategory(dto.getTitle(), category)) {
            throw new IllegalArgumentException("Pagina met deze naam bestaat al");
        }

        InfoPage page = new InfoPage();
        page.setTitle(dto.getTitle());
        page.setTldr(dto.getTldr());

        page.setSectionOneTitle(dto.getSectionOneTitle());
        page.setSectionOneContent(dto.getSectionOneContent());

        page.setSectionTwoTitle(dto.getSectionTwoTitle());
        page.setSectionTwoContent(dto.getSectionTwoContent());

        page.setSectionThreeTitle(dto.getSectionThreeTitle());
        page.setSectionThreeContent(dto.getSectionThreeContent());

        page.setSectionFourTitle(dto.getSectionFourTitle());
        page.setSectionFourContent(dto.getSectionFourContent());
        page.setInfoCategory(category);

        InfoPage saved = pageRepository.save(page);

        if ((dto.getImages() != null && dto.getImages().length > 0) ||
                (dto.getImageUrls() != null && dto.getImageUrls().length > 0)) {

            ImageCreateDto imgDto = new ImageCreateDto();
            imgDto.setImages(dto.getImages());
            imgDto.setImageUrls(dto.getImageUrls());
            imgDto.setCaptions(dto.getCaptions());

            imageService.createImages(saved.getId(), Image.ParentType.INFOPAGE, 0, imgDto);
        }

        return toPageInfoDto(saved);
    }

    public InfoPageInfoDto updatePage(int pageId, InfoPageUpdateDto dto) {
        InfoPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new IllegalArgumentException("Pagina niet gevonden"));

        if (dto.getTitle() != null) page.setTitle(dto.getTitle());
        if (dto.getTldr() != null) page.setTldr(dto.getTldr());

        if (dto.getSectionOneTitle() != null) page.setSectionOneTitle(dto.getSectionOneTitle());
        if (dto.getSectionOneContent() != null) page.setSectionOneContent(dto.getSectionOneContent());

        if (dto.getSectionTwoTitle() != null) page.setSectionTwoTitle(dto.getSectionTwoTitle());
        if (dto.getSectionTwoContent() != null) page.setSectionTwoContent(dto.getSectionTwoContent());

        if (dto.getSectionThreeTitle() != null) page.setSectionThreeTitle(dto.getSectionThreeTitle());
        if (dto.getSectionThreeContent() != null) page.setSectionThreeContent(dto.getSectionThreeContent());

        if (dto.getSectionFourTitle() != null) page.setSectionFourTitle(dto.getSectionFourTitle());
        if (dto.getSectionFourContent() != null) page.setSectionFourContent(dto.getSectionFourContent());

        InfoPage updated = pageRepository.save(page);

        if ((dto.getImages() != null && dto.getImages().length > 0) ||
                (dto.getImageUrls() != null && dto.getImageUrls().length > 0)) {

            ImageCreateDto imgDto = new ImageCreateDto();
            imgDto.setImages(dto.getImages());
            imgDto.setImageUrls(dto.getImageUrls());
            imgDto.setCaptions(dto.getCaptions());

            imageService.createImages(updated.getId(), Image.ParentType.INFOPAGE, 0, imgDto);
        }

        return toPageInfoDto(updated);
    }

    public void deletePage(int pageId) {
        InfoPage page = pageRepository.findById(pageId)
                .orElseThrow(() -> new IllegalArgumentException("Pagina niet gevonden"));

        imageService.deleteImagesByParent(page.getId(), Image.ParentType.INFOPAGE);
        pageRepository.delete(page);
    }

    public List<InfoPageMinimalDto> searchPagesByTitle(String title) {
        return pageRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public List<InfoPageMinimalDto> searchPagesByContent(String content) {
        return pageRepository
                .findBySectionOneContentContainingIgnoreCaseOrSectionTwoContentContainingIgnoreCaseOrSectionThreeContentContainingIgnoreCaseOrSectionFourContentContainingIgnoreCase(
                        content, content, content, content
                )
                .stream()
                .map(this::toMinimalDto)
                .collect(Collectors.toList());
    }

    public List<InfoPageMinimalDto> getPagesByCategory(int categoryId) {
        InfoCategory category = getCategoryById(categoryId);
        List<InfoPage> pages = pageRepository.findAllByInfoCategory(category);
        
        return pages.stream()
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