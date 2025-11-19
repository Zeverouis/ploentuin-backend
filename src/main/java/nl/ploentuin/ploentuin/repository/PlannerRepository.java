package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannerRepository extends JpaRepository<Planner,Integer> {

    List<Planner> findAllByUser(User user);

    List<Planner> findAllByUserOrderByUpdatedAtDesc(User user);
    List<Planner> findAllByUserOrderByUpdatedAtAsc(User user);

    List<Planner> findAllByUserOrderByCreatedAtDesc(User user);
    List<Planner> findAllByUserOrderByCreatedAtAsc(User user);

    boolean existsById(int id);
}
