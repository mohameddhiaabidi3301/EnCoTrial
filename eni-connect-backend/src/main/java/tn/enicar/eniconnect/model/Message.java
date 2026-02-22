package tn.enicar.eniconnect.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entité Message — messages de chat.
 * Correspond au modèle Message du chat Angular (senderId, text, timestamp,
 * isMe).
 */
@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "thread_id")
    private String threadId;

    @Column(name = "thread_type")
    private String threadType; // "dm" ou "group"

    @Column(name = "is_read")
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
