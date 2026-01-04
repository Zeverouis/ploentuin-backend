package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.InfoCategory;
import nl.ploentuin.ploentuin.model.InfoPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoPageRepository extends JpaRepository <InfoPage, Integer> {

    List<InfoPage> findAllByInfoCategory(InfoCategory category);
    List<InfoPage> findAllByOrderByUpdatedAtDesc();
    List<InfoPage> findAllByInfoCategoryOrderByTitleAsc(InfoCategory category);

    List<InfoPage> findByTitleContainingIgnoreCase(String title);

    List<InfoPage> findBySectionOneContentContainingIgnoreCaseOrSectionTwoContentContainingIgnoreCaseOrSectionThreeContentContainingIgnoreCaseOrSectionFourContentContainingIgnoreCase(String sectionOneContent, String sectionTwoContent, String sectionThreeContent, String sectionFourContent);

    boolean existsByTitleAndInfoCategory(String title, InfoCategory category);

    void deleteAllByInfoCategory(InfoCategory category);
}
