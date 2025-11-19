package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.PlannerItem;
import nl.ploentuin.ploentuin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerItemRepository extends JpaRepository<PlannerItem, Integer> {

    List<PlannerItem> findAllByPlanner(Planner planner);
    List<PlannerItem> findAllByPlannerAndType(Planner planner, PlannerItem.PlannerItemType type);

    List<PlannerItem> findAllByPlannerOrderByRowAscColumnAsc(Planner planner);

    List<PlannerItem> findAllByPlannerAndName(Planner planner, String name);
    List<PlannerItem> findAllByPlannerAndTypeAndName(Planner planner, PlannerItem.PlannerItemType type, String name);

    Optional<PlannerItem> findByIdAndPlannerAndPlannerUser(int id, Planner planner, User user);

    void deleteAllByPlanner(Planner planner);
    void deleteAllByPlannerAndType(Planner planner, PlannerItem.PlannerItemType type);
    void deleteByIdAndPlannerAndPlannerUser(int id, Planner planner, User user);
}
