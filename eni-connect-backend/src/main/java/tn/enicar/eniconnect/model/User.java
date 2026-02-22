package tn.enicar.eniconnect.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité User — représente un membre de la communauté ENI Connect.
 * Champs cohérents avec le formulaire d'inscription Angular (firstName,
 * lastName, email, password, role).
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 6, max = 120)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    @Size(max = 100)
    private String filiere;

    @Size(max = 50)
    private String promotion;

    @Size(max = 255)
    private String avatarUrl;

    @Size(max = 500)
    private String bio;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    // Relations
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Group> groups = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Nom complet pour l'affichage.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
