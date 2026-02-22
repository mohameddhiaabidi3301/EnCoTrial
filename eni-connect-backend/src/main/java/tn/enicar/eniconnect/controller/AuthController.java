package tn.enicar.eniconnect.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.enicar.eniconnect.config.JwtUtils;
import tn.enicar.eniconnect.dto.*;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.service.UserService;

import javax.validation.Valid;

/**
 * Contrôleur d'authentification — Register + Login.
 * Endpoints correspondant aux formulaires Angular login/register.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private static final Logger logger = LogManager.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * POST /api/auth/register
     * Inscription — correspond au formulaire Angular register.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);

            String token = jwtUtils.generateToken(user.getEmail());

            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .avatarUrl(user.getAvatarUrl())
                    .build();

            logger.info("Inscription réussie pour : {}", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.warn("Échec de l'inscription : {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage(), false));
        }
    }

    /**
     * POST /api/auth/login
     * Connexion — correspond au formulaire Angular login.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Email ou mot de passe incorrect");
            }

            String token = jwtUtils.generateToken(user.getEmail());

            JwtResponse response = JwtResponse.builder()
                    .token(token)
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .avatarUrl(user.getAvatarUrl())
                    .build();

            logger.info("Connexion réussie pour : {}", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.warn("Échec de la connexion : {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage(), false));
        }
    }
}
