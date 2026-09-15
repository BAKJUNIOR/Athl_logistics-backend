package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Réalisation affichée dans la galerie de la page /projets du site vitrine.
 * `featured` est prévu pour piloter plus tard la mosaïque de l'accueil (aujourd'hui encore
 * codée en dur côté front), `wide` pilote la mise en page (tuile large ou normale).
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre en français est requis")
    @Column(name = "title_fr", nullable = false)
    private String titleFr;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "caption_fr")
    private String captionFr;

    @Column(name = "caption_en")
    private String captionEn;

    private String image;

    private boolean featured;
    private boolean wide;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status = ProjectStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
