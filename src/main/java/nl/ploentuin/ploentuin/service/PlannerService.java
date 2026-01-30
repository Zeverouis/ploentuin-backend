package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.planner.*;
import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import nl.ploentuin.ploentuin.model.PlannerItemPlacement;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.PlannerItemCatalogRepository;
import nl.ploentuin.ploentuin.repository.PlannerItemPlacementRepository;
import nl.ploentuin.ploentuin.repository.PlannerRepository;
import org.springframework.data.domain.Sort;
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
        info.setUserId(planner.getUser() != null ? planner.getUser().getId() : -1);
        info.setItems(placementDtos);
        return info;
    }

    public PlannerInfoDto createPlanner(CreatePlannerDto dto, User user) {
        String title = dto.getTitle().trim();

        Planner planner = (user != null)
                ? new Planner(user, title, dto.getRows(), dto.getColumns())
                : new Planner(title, dto.getRows(), dto.getColumns());

        Planner savedPlanner = plannerRepository.save(planner);
        return toInfoDto(savedPlanner, Collections.emptyList());
    }


    public PlannerInfoDto getPlanner(int plannerId) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        List<PlannerItemPlacement> placements = placementRepository.findAllByPlannerOrderByRowAscColumnAsc(planner);
        return toInfoDto(planner, placements);
    }

    public List<PlannerListDto> getPlanners(User user, String sortBy, boolean ascending) {
        String sortField = "updatedAt".equals(sortBy) ? "updatedAt" : "createdAt";
        Sort sort = Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        return plannerRepository.findAllByUser(user, sort)
                .stream()
                .map(p -> new PlannerListDto(
                        p.getId(),
                        p.getTitle(),
                        p.getUpdatedAt(),
                        p.getCreatedAt()
                ))
                .toList();
    }

    public List<PlannerListDto> getPlanners(User user) {
        return getPlanners(user, "updated", false);
    }

    public PlannerInfoDto updatePlanner(int plannerId, UpdatePlannerDto dto, User user) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        if (planner.getUser() != null) {
            if (user == null || planner.getUser().getId() != user.getId()) {
                throw new IllegalArgumentException("Planner is niet van jou");
            }
        }

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

        List<PlannerItemPlacement> updatedPlacements =
                placementRepository.findAllByPlannerOrderByRowAscColumnAsc(planner);

        return toInfoDto(planner, updatedPlacements);
    }


    public void deletePlanner(int plannerId, User user) {
        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new IllegalArgumentException("Planner niet gevonden"));

        if (planner.getUser() != null && (user == null || planner.getUser().getId() != user.getId())) {
            throw new IllegalArgumentException("Planner is niet van jou");
        }

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