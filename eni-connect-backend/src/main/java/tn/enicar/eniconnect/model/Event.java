package tn.enicar.eniconnect.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité Event — événements de l'école.
 * Correspond au composant events Angular (date badge, time, location, category,
 * participants).
 */
@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    private String location;

    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @Column(name = "organizer_name")
    private String organizerName;

    @ManyToMany
    @JoinTable(name = "event_participants", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @Builder.Default
    private Set<User> participants = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public int getParticipantsCount() {
        return participants != null ? participants.size() : 0;
    }
}
