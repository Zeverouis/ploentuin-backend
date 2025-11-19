package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findAllByParentIdAndParentType(int parentId, Image.ParentType parentType);
    List<Image> findAllByParentType(Image.ParentType parentType);
    List<Image> findAllByParentId(int parentId);

    void deleteAllByParentIdAndParentType(int parentId, Image.ParentType parentType);

    boolean existsById(int id);
}
