package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository extends JpaRepository<ForumPost, Integer> {

    List<ForumPost> findAllByForumCategoryId(int forumCategoryId);
    List<ForumPost> findAllByUserId(int userId);

    List<ForumPost> findAllByForumCategoryIdOrderByUpdatedAtAsc(int forumCategoryId);
    List<ForumPost> findAllByForumCategoryIdOrderByUpdatedAtDesc(int forumCategoryId);

    List<ForumPost> findByTitleContainingIgnoreCase(String text);
}
