package tn.enicar.eniconnect.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entité Job — offres de stage et emploi.
 * Correspond au composant jobs Angular (company, type STAGE/CDI/CDD, skills,
 * salary).
 */
@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_logo_url")
    private String companyLogoUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "job_type")
    private String jobType; // STAGE, CDI, CDD

    private String location;

    private String salary;

    // Stocké comme chaîne séparée par des virgules (ex: "Java,Spring,Angular")
    private String skills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posted_by")
    private User postedBy;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
