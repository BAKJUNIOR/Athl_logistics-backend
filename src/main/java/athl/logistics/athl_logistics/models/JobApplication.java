package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

/**
 * Candidature soumise depuis le formulaire public de la page Carrières.
 * Soumission de visiteur, pas un contenu éditorial : le BO ne fait qu'en
 * suivre le traitement via `status`.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le poste est requis")
    @Column(nullable = false)
    private String position;

    @NotBlank(message = "Le nom est requis")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Le téléphone est requis")
    @Column(nullable = false)
    private String phone;

    private String email;

    private String experience;

    private String city;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "cv_url")
    private String cvUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status = SubmissionStatus.NEW;

    @CreationTimestamp
    @Column(name = "received_at", updatable = false)
    private Instant receivedAt;
}
