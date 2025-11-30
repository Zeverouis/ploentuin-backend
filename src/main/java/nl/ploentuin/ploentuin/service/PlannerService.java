package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.planner.*;
import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.model.PlannerItemPlacement;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.PlannerItemCatalogRepository;
import nl.ploentuin.ploentuin.repository.PlannerItemPlacementRepository;
import nl.ploentuin.ploentuin.repository.PlannerRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final PlannerItemPlacementRepository placementRepository;
    private final PlannerItemCatalogRepository catalogRepository;

    public PlannerService(
            PlannerRepository plannerRepository,
            PlannerItemPlacementRepository placementRepository,
            PlannerItemCatalogRepository catalogRepository
    ) {
        this.plannerRepository = plannerRepository;
        this.placementRepository = placementRepository;
        this.catalogRepository = catalogRepository;
    }

    private PlannerItemCatalogDto toCatalogDto(PlannerItemCatalog catalog) {
        return new PlannerItemCatalogDto(
                catalog.getId(),
                catalog.getName(),
                catalog.getColour(),
                catalog.getImageUrl(),
                catalog.getType()
        );
    }

    private PlannerItemPlacementDto toPlacementDto(PlannerItemPlacement placement) {
        return new PlannerItemPlacementDto(
                placement.getId(),
                placement.getRow(),
                placement.getColumn(),
                toCatalogDto(placement.getCatalogItem())
        );
    }

    private PlannerInfoDto toInfoDto(Planner planner, List<PlannerItemPlacement> placements) {
        List<PlannerItemPlacementDto> placementDtos = placements.stream()
                .map(this::toPlacementDto)
                .collect(Collectors.toList());

        PlannerInfoDto info = new PlannerInfoDto();
        info.setId(planner.getId());
        info.setTitle(planner.getTitle());
        info.setRows(planner.getRows());
        info.setColumns(planner.getColumns());
        info.setCreatedAt(planner.getCreatedAt());
        info.setUpdatedAt(planner.getUpdatedAt());
        info.setItems(placementDtos);
        return info;
    }

    public PlannerInfoDto createPlanner(CreatePlannerDto dto, User user, String anonymousToken) {
        String title = dto.getTitle().trim();
        String token = (user == null) ? (anonymousToken != null ? anonymousToken : UUID.randomUUID().toString()) : null;

        Planner planner = (user != null)
                ? new Planner(user, title, dto.getRows(), dto.getColumns())
                : new Planner(title, dto.getRows(), dto.getColumns(), token);

        Planner savedPlanner = plannerRepository.save(planner);
        return toInfoDto(savedPlanner, Collections.emptyList());
    }

    public PlannerInfoDto getPlanner(int plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        List<PlannerItemPlacement> placements = placementRepository.findAllByPlannerOrderByRowAscColumnAsc(planner);
        return toInfoDto(planner, placements);
    }

    public PlannerInfoDto getPlannerByAnonymousToken(String token) {
        Planner planner = plannerRepository.findByAnonymousToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        List<PlannerItemPlacement> placements = placementRepository.findAllByPlannerOrderByRowAscColumnAsc(planner);
        return toInfoDto(planner, placements);
    }

    public PlannerInfoDto updatePlanner(int plannerId, UpdatePlannerDto dto, User user, String anonymousToken) {
        Planner planner = (user != null)
                ? plannerRepository.findByIdAndUser(plannerId, user)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"))
                : plannerRepository.findByAnonymousToken(anonymousToken)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        if (dto.getTitle() != null) planner.setTitle(dto.getTitle());
        planner.setRows(dto.getRows());
        planner.setColumns(dto.getColumns());

        plannerRepository.save(planner);

        placementRepository.deleteAllByPlanner(planner);

        if (dto.getItems() != null) {
            for (PlannerItemPlacementDto itemDto : dto.getItems()) {
                PlannerItemPlacement placement = new PlannerItemPlacement();
                placement.setPlanner(planner);
                placement.setCatalogItem(
                        catalogRepository.findById(itemDto.getCatalogItem().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Catalog item niet gevonden"))
                );
                placement.setRow(itemDto.getRow());
                placement.setColumn(itemDto.getColumn());
                placementRepository.save(placement);
            }
        }

        List<PlannerItemPlacement> updatedPlacements = placementRepository.findAllByPlannerOrderByRowAscColumnAsc(planner);

        return toInfoDto(planner, updatedPlacements);
    }

    public void deletePlanner(int plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        placementRepository.deleteAllByPlanner(planner);
        plannerRepository.delete(planner);
    }

    public PlannerItemPlacementDto placeItem(int plannerId, int catalogItemId, int row, int column) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Planner not found"));

        PlannerItemCatalog catalog = catalogRepository.findById(catalogItemId)
                .orElseThrow(() -> new IllegalArgumentException("Catalog item niet gevonden"));

        PlannerItemPlacement placement = new PlannerItemPlacement();
        placement.setPlanner(planner);
        placement.setCatalogItem(catalog);
        placement.setRow(row);
        placement.setColumn(column);

        PlannerItemPlacement saved = placementRepository.save(placement);
        return toPlacementDto(saved);
    }

    public void removePlacement(int placementId) {
        if (!placementRepository.existsById(placementId))
            throw new IllegalArgumentException("Placement niet gevonden");

        placementRepository.deleteById(placementId);
    }
}