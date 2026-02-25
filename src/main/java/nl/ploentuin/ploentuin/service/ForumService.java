package nl.ploentuin.ploentuin.service;

import nl.ploentuin.ploentuin.dto.forum.*;
import nl.ploentuin.ploentuin.dto.image.ImageCreateDto;
import nl.ploentuin.ploentuin.dto.image.ImageResponseDto;
import nl.ploentuin.ploentuin.model.Comment;
import nl.ploentuin.ploentuin.model.ForumCategory;
import nl.ploentuin.ploentuin.model.ForumPost;
import nl.ploentuin.ploentuin.model.Image;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.CommentRepository;
import nl.ploentuin.ploentuin.repository.ForumCategoryRepository;
import nl.ploentuin.ploentuin.repository.ForumPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForumService {

    private final ForumPostRepository postRepository;
    private final ForumCategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ImageService imageService;

    public ForumService(
            ForumPostRepository postRepository,
            ForumCategoryRepository categoryRepository,
            CommentRepository commentRepository,
            ImageService imageService
    ) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.imageService = imageService;
    }

    private ForumPostResponseDto toPostDto(ForumPost post) {
        List<CommentResponseDto> comments = commentRepository.findAllByForumPostIdOrderByCreatedAtAsc(post.getId())
                .stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());

        List<ImageResponseDto> images = imageService.getImagesByParent(post.getId(), Image.ParentType.FORUMPOST);

        return new ForumPostResponseDto(
                post.getId(),
                post.getUser().getId(),
                post.getForumCategory().getId(),
                post.getUser().getUsername(),
                post.getForumCategory().getCategoryName(),
                post.getTitle(),
                post.getContent(),
                post.getUser().isBanned(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getUser().getRole().name(),
                post.getUser().getAvatarUrl(),
                comments,
                images
        );
    }

    private CommentResponseDto toCommentDto(Comment comment) {
        List<ImageResponseDto> images = imageService.getImagesByParent(comment.getId(), Image.ParentType.COMMENT);

        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getId(),
                comment.getForumPost().getId(),
                comment.getUser().getUsername(),
                comment.getContent(),
                comment.getUser().isBanned(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getUser().getRole().name(),
                comment.getUser().getAvatarUrl(),
                images
        );
    }

    public List<ForumCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ForumCategory getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categorie niet gevonden"));
    }

    public ForumCategory createCategory(String categoryName) {
        if (categoryRepository.findByCategoryName(categoryName).isPresent()) {
            throw new IllegalArgumentException("Categorie bestaat al");
        }
        ForumCategory category = new ForumCategory();
        category.setCategoryName(categoryName);
        return categoryRepository.save(category);
    }

    public ForumCategory getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Categorie niet gevonden"));
    }

    @Transactional
    public ForumPostResponseDto createPost(ForumPostCreateDto dto, User user, int categoryId) {
        if (user.isBanned()) {
            throw new IllegalArgumentException("Je kunt geen berichten plaatsen omdat je account is verbannen.");
        }

        ForumCategory category = getCategoryById(categoryId);

        ForumPost post = new ForumPost();
        post.setUser(user);
        post.setForumCategory(category);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        ForumPost saved = postRepository.save(post);

        if ((dto.getImages() != null && dto.getImages().length > 0) ||
                (dto.getImageUrls() != null && dto.getImageUrls().length > 0)) {

            ImageCreateDto imgDto = new ImageCreateDto();
            imgDto.setImages(dto.getImages());
            imgDto.setImageUrls(dto.getImageUrls());
            imageService.createImages(saved.getId(), Image.ParentType.FORUMPOST, user.getId(), imgDto);
        }

        return toPostDto(saved);
    }

    public ForumPostResponseDto getPostById(int postId) {
        ForumPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post niet gevonden"));
        return toPostDto(post);
    }

    public List<ForumPostResponseDto> getPostsByUser(int userId) {
        return postRepository.findAllByUserId(userId)
                .stream()
                .map(this::toPostDto)
                .collect(Collectors.toList());
    }

    public List<ForumPostResponseDto> getPostsByCategory(int categoryId) {
        return postRepository.findAllByForumCategoryIdOrderByUpdatedAtDesc(categoryId)
                .stream()
                .map(this::toPostDto)
                .collect(Collectors.toList());
    }

    public List<ForumPostResponseDto> getPostsByCategoryUnordered(int categoryId) {
        return postRepository.findAllByForumCategoryId(categoryId)
                .stream()
                .map(this::toPostDto)
                .collect(Collectors.toList());
    }

    public List<ForumPostResponseDto> searchPostsByTitle(String text) {
        return postRepository.findByTitleContainingIgnoreCase(text)
                .stream()
                .map(this::toPostDto)
                .collect(Collectors.toList());
    }

    public List<ForumPostResponseDto> getLatestPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .limit(5)
                .map(this::toPostDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ForumPostResponseDto updatePost(int postId, ForumPostCreateDto dto, User user) {
        ForumPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post niet gevonden"));

        if (post.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("Je hebt geen permissie om deze post te bewerken");
        }

        if (dto.getTitle() != null) post.setTitle(dto.getTitle());
        if (dto.getContent() != null) post.setContent(dto.getContent());

        ForumPost saved = postRepository.save(post);

        if ((dto.getImages() != null && dto.getImages().length > 0) ||
                (dto.getImageUrls() != null && dto.getImageUrls().length > 0)) {

            ImageCreateDto imgDto = new ImageCreateDto();
            imgDto.setImages(dto.getImages());
            imgDto.setImageUrls(dto.getImageUrls());
            imageService.createImages(saved.getId(), Image.ParentType.FORUMPOST, user.getId(), imgDto);
        }

        return toPostDto(saved);
    }

    @Transactional
    public CommentResponseDto updateComment(int commentId, CommentCreateDto dto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment niet gevonden"));

        if (comment.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("Gij zijt niet de eigenaar van deze comment!");
        }

        if (dto.getContent() != null) {
            comment.setContent(dto.getContent());
        }

        Comment saved = commentRepository.save(comment);

        if ((dto.getImages() != null && dto.getImages().length > 0) ||
                (dto.getImageUrls() != null && dto.getImageUrls().length > 0)) {

            ImageCreateDto imgDto = new ImageCreateDto();
            imgDto.setImages(dto.getImages());
            imgDto.setImageUrls(dto.getImageUrls());
            imageService.createImages(saved.getId(), Image.ParentType.COMMENT, user.getId(), imgDto);
        }

        return toCommentDto(saved);
    }

    @Transactional
    public void deletePost(int postId, User user) {
        ForumPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post niet gevonden"));

        boolean isOwner = post.getUser().getId() == user.getId();
        boolean isAdmin = user.getRole().name().equals("ADMIN") || user.getRole().name().equals("MOD");

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("Gij hebt hier niks te vertellen! (Geen permissie)");
        }

        List<Comment> comments = commentRepository.findAllByForumPostId(postId);
        for (Comment comment : comments) {
            imageService.deleteImagesByParent(comment.getId(), Image.ParentType.COMMENT);
        }
        commentRepository.deleteAllByForumPostId(postId);

        imageService.deleteImagesByParent(postId, Image.ParentType.FORUMPOST);

        postRepository.delete(post);
    }

    @Transactional
    public CommentResponseDto addComment(int postId, CommentCreateDto dto, User user) {
        if (user.isBanned()) {
            throw new IllegalArgumentException("Je kunt geen berichten plaatsen omdat je account is verbannen.");
        }

        ForumPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post niet gevonden"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setForumPost(post);
        comment.setContent(dto.getContent());

        Comment saved = commentRepository.save(comment);

        if ((dto.getImages() != null && dto.getImages().length > 0) ||
                (dto.getImageUrls() != null && dto.getImageUrls().length > 0)) {

            ImageCreateDto imgDto = new ImageCreateDto();
            imgDto.setImages(dto.getImages());
            imgDto.setImageUrls(dto.getImageUrls());
            imageService.createImages(saved.getId(), Image.ParentType.COMMENT, user.getId(), imgDto);
        }

        return toCommentDto(saved);
    }

    public List<CommentResponseDto> getCommentsByUser(int userId) {
        return commentRepository.findAllByUserId(userId)
                .stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteComment(int commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment niet gevonden"));

        boolean isOwner = comment.getUser().getId() == user.getId();
        boolean isAdmin = user.getRole().name().equals("ADMIN") || user.getRole().name().equals("MOD");

        if (!isOwner && !isAdmin) {
            throw new IllegalArgumentException("Ge hebt geen permissie om deze comment te verwijderen");
        }

        imageService.deleteImagesByParent(comment.getId(), Image.ParentType.COMMENT);
        commentRepository.delete(comment);
    }

    @Transactional
    public void deleteAllCommentsByUser(int userId) {
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        for (Comment comment : comments) {
            imageService.deleteImagesByParent(comment.getId(), Image.ParentType.COMMENT);
        }
        commentRepository.deleteAllByUserId(userId);
    }

    @Transactional
    public void deleteCategory(int id, User admin) {
        ForumCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categorie niet gevonden"));
        List<ForumPost> posts = postRepository.findAllByForumCategoryId(id);
        for (ForumPost post : posts) {
            deletePost(post.getId(), admin);
        }
        categoryRepository.delete(category);
    }
}