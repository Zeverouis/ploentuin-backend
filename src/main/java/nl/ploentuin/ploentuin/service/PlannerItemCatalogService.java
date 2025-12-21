package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.planner.PlannerItemCatalogDto;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.repository.PlannerItemCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlannerItemCatalogService {

    private final PlannerItemCatalogRepository catalogRepository;

    public PlannerItemCatalogService(PlannerItemCatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }

    private PlannerItemCatalogDto toDto(PlannerItemCatalog item) {
        return new PlannerItemCatalogDto(
                item.getId(),
                item.getName(),
                item.getColour(),
                item.getImageUrl(),
                item.getType()
        );
    }

    public PlannerItemCatalogDto createItem(PlannerItemCatalog item) {
        PlannerItemCatalog saved = catalogRepository.save(item);
        return toDto(saved);
    }

    public List<PlannerItemCatalogDto> getAllItems() {
        return catalogRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PlannerItemCatalogDto> getItemsByType(PlannerItemCatalog.PlannerItemType type) {
        return catalogRepository.findAllByType(type)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PlannerItemCatalogDto getById(int id) {
        return catalogRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    public void deleteItem(int id) {
        if (!catalogRepository.existsById(id)) {
            throw new IllegalArgumentException("Catalog item niet gevonden");
        }
        catalogRepository.deleteById(id);
    }
}