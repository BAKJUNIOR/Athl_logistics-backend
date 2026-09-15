package athl.logistics.athl_logistics.models;

import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Demande de devis soumise depuis le formulaire public du site vitrine
 * (quote-modal / page Devis). Soumission de visiteur, pas un contenu éditorial :
 * le BO ne fait qu'en suivre le traitement via `status`.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "quote_requests")
public class QuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le service est requis")
    @Column(name = "service_label", nullable = false)
    private String serviceLabel;

    @NotBlank(message = "Le nom est requis")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Le téléphone est requis")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "La description est requise")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "quote_request_attachments", joinColumns = @JoinColumn(name = "quote_request_id"))
    @Column(name = "url")
    @OrderColumn(name = "sort_order")
    private List<String> attachments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status = SubmissionStatus.NEW;

    @CreationTimestamp
    @Column(name = "received_at", updatable = false)
    private Instant receivedAt;
}
