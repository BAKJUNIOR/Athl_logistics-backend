package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.ServiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Prestation proposée par ATHL, affichée sur /services et /services/:slug du site vitrine.
 * `slug` est généré une seule fois par le backend à la création (voir ServiceOfferingServiceImpl)
 * et reste ensuite immuable — le BO ne l'édite jamais, il ne fait que l'afficher.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "services")
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String slug;

    private String number;

    @NotBlank(message = "Le titre en français est requis")
    @Column(name = "title_fr", nullable = false)
    private String titleFr;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "short_title_fr")
    private String shortTitleFr;

    @Column(name = "short_title_en")
    private String shortTitleEn;

    @NotBlank(message = "Le texte de présentation en français est requis")
    @Column(name = "lead_fr", columnDefinition = "TEXT", nullable = false)
    private String leadFr;

    @Column(name = "lead_en", columnDefinition = "TEXT")
    private String leadEn;

    private String image;

    @Column(name = "hero_image")
    private String heroImage;

    @ElementCollection
    @CollectionTable(name = "service_gallery", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "image_url")
    @OrderColumn(name = "sort_order")
    private List<String> gallery = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<ServicePrestation> prestations = new ArrayList<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<ServiceProcessStep> processSteps = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceStatus status = ServiceStatus.DRAFT;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}
