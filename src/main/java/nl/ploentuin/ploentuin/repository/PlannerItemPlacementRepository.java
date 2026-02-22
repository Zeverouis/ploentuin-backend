package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.PlannerItemPlacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerItemPlacementRepository extends JpaRepository<PlannerItemPlacement, Integer> {
    List<PlannerItemPlacement> findAllByPlannerOrderByRowAscColumnAsc(Planner planner);

    void deleteAllByPlanner(Planner planner);

    Optional<PlannerItemPlacement> findByPlannerAndRowAndColumn(Planner planner, int row, int column);
}