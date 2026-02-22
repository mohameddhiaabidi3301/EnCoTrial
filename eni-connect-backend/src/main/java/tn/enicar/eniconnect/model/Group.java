package tn.enicar.eniconnect.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité Group — groupes/clubs de la communauté ENI.
 * Correspond au composant groups Angular.
 */
@Entity
@Table(name = "groups_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(name = "cover_url")
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToMany
    @JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> members = new HashSet<>();

    @Column(name = "posts_count")
    @Builder.Default
    private Integer postsCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public int getMembersCount() {
        return members != null ? members.size() : 0;
    }
}
