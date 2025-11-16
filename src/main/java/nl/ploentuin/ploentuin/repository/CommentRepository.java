package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByForumPostId(int forumPostId);

    List<Comment> findAllByForumPostIdOrderByCreatedAtAsc(int forumPostId);

    List<Comment> findAllByUserId(int userId);
}
