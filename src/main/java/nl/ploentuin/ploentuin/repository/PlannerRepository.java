package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Planner;
import nl.ploentuin.ploentuin.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlannerRepository extends JpaRepository<Planner,Integer> {

    List<Planner> findAllByUser(User user, Sort sort);
    Optional<Planner> findByIdAndUser(int id, User user);

    Optional<Planner> findByAnonymousToken(String token);
}
