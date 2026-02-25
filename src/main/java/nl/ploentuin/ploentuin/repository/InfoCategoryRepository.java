package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.InfoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InfoCategoryRepository extends JpaRepository <InfoCategory, Integer> {

    Optional<InfoCategory> findByCategoryName(String categoryName);
    List<InfoCategory> findAllByOrderByCategoryNameAsc();
    List<InfoCategory> findAllByOrderByCategoryNameDesc();

    boolean existsByCategoryName(String categoryName);

    void deleteByCategoryName(String categoryName);
}
