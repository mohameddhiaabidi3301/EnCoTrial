package tn.enicar.enicarconnect.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentEmail;
    private String subject;
    private Double score;
    private String semester;
}
