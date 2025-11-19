package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.PlannerItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlannerItemRepository extends JpaRepository<PlannerItem, Integer> {

    List<PlannerItem> findAllByPlanner(Planner planner);
    List<PlannerItem> findAllByPlannerAndType(Planner planner, PlannerItem.PlannerItemType type);

    List<PlannerItem> findAllByPlannerOrderByRowAscColumnAsc(Planner planner);

    List<PlannerItem> findAllByPlannerAndName(Planner planner, String name);
    List<PlannerItem> findAllByPlannerAndTypeAndName(Planner planner, PlannerItem.PlannerItemType type, String name);

    void deleteAllByPlanner(Planner planner);
}
