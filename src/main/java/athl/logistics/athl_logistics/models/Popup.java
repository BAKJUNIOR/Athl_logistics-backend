package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.PopupFrequency;
import athl.logistics.athl_logistics.models.enums.PopupLayout;
import athl.logistics.athl_logistics.models.enums.PopupType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

/**
 * Popup marketing ciblée sur une page précise du site vitrine (voir Popup côté front/BO).
 * Règle métier : une seule popup active par page à la fois — appliquée dans
 * PopupServiceImpl.activate(), pas ici (une entité ne connaît pas ses "sœurs").
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "popups")
public class Popup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La page cible est requise")
    @Column(nullable = false)
    private String page = "";

    @Column(nullable = false)
    private boolean active;

    @NotNull(message = "Le type est requis")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PopupType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PopupLayout layout = PopupLayout.STACKED;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PopupFrequency frequency = PopupFrequency.ONCE_PER_VISITOR;

    @Column(name = "delay_ms")
    private int delayMs = 1200;

    private String eyebrow;

    @NotBlank(message = "Le titre est requis")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String text;

    private String image;
    private String video;

    @Column(name = "collect_email")
    private boolean collectEmail;

    @Column(name = "cta_label")
    private String ctaLabel;

    @Column(name = "cta_url")
    private String ctaUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
