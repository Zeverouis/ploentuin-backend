package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.PlannerItemPlacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerItemPlacementRepository extends JpaRepository<PlannerItemPlacement, Integer> {
    List<PlannerItemPlacement> findAllByPlannerOrderByRowAscColumnAsc(Planner planner);

    void deleteAllByPlanner(Planner planner);
}