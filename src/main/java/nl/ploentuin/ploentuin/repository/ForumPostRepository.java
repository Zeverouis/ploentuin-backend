package nl.ploentuin.ploentuin.repository;

import nl.ploentuin.ploentuin.model.ForumPost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumPostRepository {

    List<ForumPost> findAllByForumCategoryId(int forumCategoryId);

    List<ForumPost> findAllByUserId(int userId);

    List<ForumPost> FindAllByForumCategoryIdOrderByCreatedAtAsc(int forumCategoryId);

}
