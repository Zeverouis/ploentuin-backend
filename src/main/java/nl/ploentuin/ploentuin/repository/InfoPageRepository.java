package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.InfoCategory;
import nl.ploentuin.ploentuin.model.InfoPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoPageRepository extends JpaRepository <InfoPage, Integer> {

    List<InfoPage> findAllByInfoCategory(InfoCategory category);
    List<InfoPage> findAllByInfoCategoryOrderByUpdatedAtAsc(InfoCategory category);
    List<InfoPage> findAllByInfoCategoryOrderByUpdatedAtDesc(InfoCategory category);
    List<InfoPage> findAllByOrderByUpdatedAtDesc();
    List<InfoPage> findAllByInfoCategoryOrderByTitleAsc(InfoCategory category);
    List<InfoPage> findAllByInfoCategoryOrderByTitleDesc(InfoCategory category);

    List<InfoPage> findByTitleContainingIgnoreCase(String title);

    List<InfoPage> findByContentContainingIgnoreCase(String text);

    boolean existsByTitleAndInfoCategory(String title, InfoCategory category);
    boolean existsByTitleAndInfoCategoryAndIdNot(String title, InfoCategory category, int id);

    void deleteAllByInfoCategory(InfoCategory category);
}
