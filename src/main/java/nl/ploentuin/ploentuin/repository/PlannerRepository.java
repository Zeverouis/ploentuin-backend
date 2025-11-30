package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner,Integer> {

    List<Planner> findAllByUser(User user);
    Optional<Planner> findByIdAndUser(int id, User user);

    List<Planner> findAllByUserOrderByUpdatedAtDesc(User user);
    List<Planner> findAllByUserOrderByUpdatedAtAsc(User user);

    List<Planner> findAllByUserOrderByCreatedAtDesc(User user);
    List<Planner> findAllByUserOrderByCreatedAtAsc(User user);

    Optional<Planner> findByAnonymousToken(String token);
    boolean existsByAnonymousToken(String token);

    boolean existsById(int id);
    boolean existsByIdAndUser(int id, User user);
}
