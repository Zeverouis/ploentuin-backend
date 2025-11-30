package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.PlannerItemCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerItemCatalogRepository extends JpaRepository<PlannerItemCatalog, Integer> {
    List<PlannerItemCatalog> findAllByType(PlannerItemCatalog.PlannerItemType type);
}