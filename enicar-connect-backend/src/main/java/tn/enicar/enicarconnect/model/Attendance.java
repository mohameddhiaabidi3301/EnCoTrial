package tn.enicar.enicarconnect.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendances")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentEmail;
    private String courseCode;
    private LocalDateTime scannedAt;
    private Boolean present;

    @PrePersist
    public void prePersist() {
        if (scannedAt == null) scannedAt = LocalDateTime.now();
    }
}
