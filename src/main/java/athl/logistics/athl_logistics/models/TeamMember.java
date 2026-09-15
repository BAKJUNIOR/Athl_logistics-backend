package athl.logistics.athl_logistics.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Membre de l'équipe dirigeante affiché sur la page Équipe du site vitrine.
 * Pas de notion de brouillon/publié ici (contrairement à Services/Jobs) : un membre listé
 * est visible, point — le retirer de la page se fait en le supprimant.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "team_members")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est requis")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Le rôle en français est requis")
    @Column(name = "role_fr", nullable = false)
    private String roleFr;

    @Column(name = "role_en")
    private String roleEn;

    private String photo;

    @Column(name = "sort_order")
    private int sortOrder;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
