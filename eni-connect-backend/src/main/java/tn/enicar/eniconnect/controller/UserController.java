package tn.enicar.eniconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.service.UserService;

import java.util.List;

/**
 * Contrôleur utilisateur — profil, liste utilisateurs.
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET /api/users/me — Profil de l'utilisateur connecté.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("Non authentifié");
        }
        return ResponseEntity.ok(user);
    }

    /**
     * GET /api/users — Liste de tous les utilisateurs.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/users/{id} — Mise à jour du profil.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedData) {
        try {
            User updated = userService.updateProfile(id, updatedData);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
