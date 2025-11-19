package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Integer> {

    Optional<ForumCategory> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);
}
