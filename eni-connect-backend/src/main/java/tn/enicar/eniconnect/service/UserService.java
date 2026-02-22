package tn.enicar.eniconnect.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.enicar.eniconnect.dto.RegisterRequest;
import tn.enicar.eniconnect.model.Role;
import tn.enicar.eniconnect.model.User;
import tn.enicar.eniconnect.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Inscription d'un nouvel utilisateur.
     */
    public User register(RegisterRequest request) {
        logger.info("Tentative d'inscription pour : {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Email déjà utilisé : {}", request.getEmail());
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(parseRole(request.getRole()))
                .filiere(request.getFiliere())
                .promotion(request.getPromotion())
                .avatarUrl(generateAvatarUrl(request.getFirstName(), request.getLastName()))
                .build();

        User saved = userRepository.save(user);
        logger.info("Utilisateur créé avec succès : {} (id={})", saved.getEmail(), saved.getId());
        return saved;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User updateProfile(Long userId, User updatedData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (updatedData.getFirstName() != null)
            user.setFirstName(updatedData.getFirstName());
        if (updatedData.getLastName() != null)
            user.setLastName(updatedData.getLastName());
        if (updatedData.getBio() != null)
            user.setBio(updatedData.getBio());
        if (updatedData.getFiliere() != null)
            user.setFiliere(updatedData.getFiliere());
        if (updatedData.getPromotion() != null)
            user.setPromotion(updatedData.getPromotion());
        if (updatedData.getAvatarUrl() != null)
            user.setAvatarUrl(updatedData.getAvatarUrl());

        logger.info("Profil mis à jour pour : {}", user.getEmail());
        return userRepository.save(user);
    }

    /**
     * Génère l'URL avatar UI Avatars (cohérent avec le frontend Angular).
     */
    private String generateAvatarUrl(String firstName, String lastName) {
        return "https://ui-avatars.com/api/?name=" + firstName + "+" + lastName
                + "&background=0071e3&color=fff";
    }

    /**
     * Parse le rôle depuis la chaîne du formulaire Angular.
     */
    private Role parseRole(String roleStr) {
        if (roleStr == null || roleStr.isEmpty())
            return Role.STUDENT;

        switch (roleStr.toLowerCase()) {
            case "teacher":
                return Role.TEACHER;
            case "staff":
                return Role.STAFF;
            case "direction":
                return Role.DIRECTION;
            case "alumni":
                return Role.ALUMNI;
            default:
                return Role.STUDENT;
        }
    }
}
