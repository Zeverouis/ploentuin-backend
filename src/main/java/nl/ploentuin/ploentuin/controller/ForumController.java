package nl.ploentuin.ploentuin.controller;

import jakarta.validation.Valid;
import nl.ploentuin.ploentuin.dto.api.ApiResponse;
import nl.ploentuin.ploentuin.dto.api.ResponseHelper;
import nl.ploentuin.ploentuin.dto.forum.*;
import nl.ploentuin.ploentuin.model.ForumCategory;
import nl.ploentuin.ploentuin.model.User;
import nl.ploentuin.ploentuin.repository.UserRepository;
import nl.ploentuin.ploentuin.service.ForumService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forums")
public class ForumController {

    private final ForumService forumService;
    private final UserRepository userRepository;

    public ForumController(ForumService forumService, UserRepository userRepository) {
        this.forumService = forumService;
        this.userRepository = userRepository;
    }

    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<ForumCategory>>> getAllCategories() {
        return ResponseHelper.ok(forumService.getAllCategories(), "Categorieën opgehaald");
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<ForumCategory>> getCategory(@PathVariable int id) {
        try {
            return ResponseHelper.ok(forumService.getCategoryById(id), "Categorie opgehaald");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ForumCategory>> createCategory(
            @RequestBody @Valid ForumCategoryCreateDto dto
    ) {
        ForumCategory category = forumService.createCategory(dto.getCategoryName());
        return ResponseHelper.ok(category, "Categorie aangemaakt");
    }

    @GetMapping("/categories/name/{categoryName}")
    public ResponseEntity<ApiResponse<ForumCategory>> getCategoryByName(@PathVariable String categoryName) {
        try {
            ForumCategory category = forumService.getCategoryByName(categoryName);
            return ResponseHelper.ok(category, "Categorie opgehaald");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @GetMapping("/posts/latest")
    public ResponseEntity<ApiResponse<List<ForumPostResponseDto>>> getLatestPosts() {
        return ResponseHelper.ok(forumService.getLatestPosts(), "Nieuwste posts opgehaald");
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<ForumPostResponseDto>> getPost(@PathVariable int postId) {
        try {
            return ResponseHelper.ok(forumService.getPostById(postId), "Post opgehaald");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.notFound(e.getMessage());
        }
    }

    @GetMapping("/users/{username}/posts")
    public ResponseEntity<ApiResponse<List<ForumPostResponseDto>>> getPostsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden"));
        return ResponseHelper.ok(forumService.getPostsByUser(user.getId()), "Posts van gebruiker opgehaald");
    }


    @GetMapping("/categories/{categoryId}/posts")
    public ResponseEntity<ApiResponse<List<ForumPostResponseDto>>> getPostsByCategory(@PathVariable int categoryId) {
        return ResponseHelper.ok(forumService.getPostsByCategory(categoryId), "Posts per categorie opgehaald");
    }

    @GetMapping("/categories/{categoryId}/posts/unordered")
    public ResponseEntity<ApiResponse<List<ForumPostResponseDto>>> getPostsByCategoryUnordered(
            @PathVariable int categoryId
    ) {
        return ResponseHelper.ok(
                forumService.getPostsByCategoryUnordered(categoryId),
                "Posts per categorie opgehaald (niet gesorteerd)"
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ForumPostResponseDto>>> searchPosts(
            @RequestParam String query
    ) {
        return ResponseHelper.ok(forumService.searchPostsByTitle(query), "Zoekresultaten opgehaald");
    }


    @PostMapping(value = "/categories/{categoryId}/posts", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<ForumPostResponseDto>> createPost(
            @PathVariable int categoryId,
            @Valid @ModelAttribute ForumPostCreateDto dto,
            Authentication auth
    ) {
        User user = getCurrentUser(auth);
        if (user == null) return ResponseHelper.forbidden("Je moet ingelogd zijn om een post te maken");

        try {
            return ResponseHelper.ok(forumService.createPost(dto, user, categoryId), "Post aangemaakt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PatchMapping(value = "/posts/{postId}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<ForumPostResponseDto>> updatePost(
            @PathVariable int postId,
            @ModelAttribute ForumPostCreateDto dto,
            Authentication auth
    ) {
        User user = getCurrentUser(auth);
        if (user == null) return ResponseHelper.forbidden("Je moet ingelogd zijn om een post te bewerken");

        try {
            return ResponseHelper.ok(forumService.updatePost(postId, dto, user), "Post bijgewerkt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.forbidden(e.getMessage());
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @PathVariable int postId,
            Authentication auth
    ) {
        User user = getCurrentUser(auth);
        if (user == null) return ResponseHelper.forbidden("Je moet ingelogd zijn om een post te verwijderen");

        try {
            forumService.deletePost(postId, user);
            return ResponseHelper.ok(null, "Post verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.forbidden(e.getMessage());
        }
    }

    @PostMapping(value = "/posts/{postId}/comments", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<CommentResponseDto>> addComment(
            @PathVariable int postId,
            @Valid @ModelAttribute CommentCreateDto dto,
            Authentication auth
    ) {
        User user = getCurrentUser(auth);
        if (user == null) return ResponseHelper.forbidden("Je moet ingelogd zijn om te reageren");

        try {
            return ResponseHelper.ok(forumService.addComment(postId, dto, user), "Comment toegevoegd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.badRequest(e.getMessage());
        }
    }

    @PatchMapping(value = "/comments/{commentId}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<CommentResponseDto>> updateComment(
            @PathVariable int commentId,
            @ModelAttribute CommentCreateDto dto,
            Authentication auth
    ) {
        User user = getCurrentUser(auth);
        if (user == null) return ResponseHelper.forbidden("Inloggen ffe aub.");

        try {
            return ResponseHelper.ok(forumService.updateComment(commentId, dto, user), "Comment bijgewerkt");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.forbidden(e.getMessage());
        }
    }

    @GetMapping("/users/{username}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getCommentsByUsername(@PathVariable String username) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden"));
        return ResponseHelper.ok(forumService.getCommentsByUser(user.getId()), "Comments van gebruiker opgehaald");
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable int commentId,
            Authentication auth
    )
    {
        User user = getCurrentUser(auth);
        if (user == null) return ResponseHelper.forbidden("Ge bent geen admin of mod ofwel? Of niet ingelogd!");

        try {
            forumService.deleteComment(commentId, user);
            return ResponseHelper.ok(null, "Comment verwijderd");
        } catch (IllegalArgumentException e) {
            return ResponseHelper.forbidden(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
    @DeleteMapping("/users/{username}/comments")
    public ResponseEntity<ApiResponse<Void>> deleteAllCommentsByUsername(
            @PathVariable String username,
            Authentication auth
    )
    {
        User adminUser = getCurrentUser(auth);
        if (adminUser == null) return ResponseHelper.forbidden("Bende gij wel een admin/mod? Zo ja, log dan ffe in.");

        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden"));

        forumService.deleteAllCommentsByUser(user.getId());
        return ResponseHelper.ok(null, "Alle comments van gebruiker verwijderd");
    }


    private User getCurrentUser(Authentication auth) {
        if (auth == null) return null;
        return userRepository.findByUsernameIgnoreCase(auth.getName()).orElse(null);
    }
}