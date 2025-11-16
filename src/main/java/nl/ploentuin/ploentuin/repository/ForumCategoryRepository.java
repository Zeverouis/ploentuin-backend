package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.ForumCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Integer> {

    ForumCategory findByCategoryName(String categoryName);
}
