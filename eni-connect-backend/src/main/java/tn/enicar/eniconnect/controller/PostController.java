package tn.enicar.eniconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.model.Comment;
import tn.enicar.eniconnect.model.Post;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.service.PostService;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur des publications — correspond au feed Angular.
 */
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * GET /api/posts — Fil d'actualité (toutes les publications).
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /**
     * GET /api/posts/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    /**
     * POST /api/posts — Créer une publication.
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body) {
        Post post = postService.createPost(
                user.getId(),
                body.get("content"),
                body.get("imageUrl"));
        return ResponseEntity.ok(post);
    }

    /**
     * PUT /api/posts/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
            @RequestBody Map<String, String> body) {
        Post post = postService.updatePost(id, body.get("content"), body.get("imageUrl"));
        return ResponseEntity.ok(post);
    }

    /**
     * DELETE /api/posts/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    /**
     * POST /api/posts/{id}/like — Toggle like.
     */
    @PostMapping("/{id}/like")
    public ResponseEntity<Post> toggleLike(@PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.toggleLike(id, user.getId()));
    }

    /**
     * GET /api/posts/{id}/comments
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getComments(id));
    }

    /**
     * POST /api/posts/{id}/comments — Ajouter un commentaire.
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body) {
        Comment comment = postService.addComment(id, user.getId(), body.get("content"));
        return ResponseEntity.ok(comment);
    }
}
