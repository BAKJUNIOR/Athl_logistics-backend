package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.JobBullet;
import athl.logistics.athl_logistics.models.JobOffer;
import athl.logistics.athl_logistics.models.enums.JobBulletKind;
import athl.logistics.athl_logistics.models.enums.JobStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// Une seule forme pour la liste ET le détail (pas de version allégée) : la page /carrieres
// du front affiche toutes les offres en accordéon sur une seule requête, missions/profil
// compris — contrairement à Services, il n'y a pas de page détail séparée à charger à la demande.
@Data
@NoArgsConstructor
public class JobOfferDTO {
    private Long id;
    private JobDomainDTO domain;
    private String titleFr;
    private String titleEn;
    private String descriptionFr;
    private String descriptionEn;
    private String metaFr;
    private String metaEn;
    private LocalDate publishedAt;
    private LocalDate deadline;
    private List<JobBulletDTO> missions;
    private List<JobBulletDTO> profile;
    private String contactPhone;
    private JobStatus status;
    private Instant updatedAt;

    public JobOfferDTO(JobOffer entity) {
        this.id = entity.getId();
        this.domain = entity.getDomain() != null ? new JobDomainDTO(entity.getDomain()) : null;
        this.titleFr = entity.getTitleFr();
        this.titleEn = entity.getTitleEn();
        this.descriptionFr = entity.getDescriptionFr();
        this.descriptionEn = entity.getDescriptionEn();
        this.metaFr = entity.getMetaFr();
        this.metaEn = entity.getMetaEn();
        this.publishedAt = entity.getPublishedAt();
        this.deadline = entity.getDeadline();
        this.missions = entity.getBullets().stream()
                .filter(b -> b.getKind() == JobBulletKind.MISSION)
                .map(JobBulletDTO::new)
                .collect(Collectors.toList());
        this.profile = entity.getBullets().stream()
                .filter(b -> b.getKind() == JobBulletKind.PROFILE)
                .map(JobBulletDTO::new)
                .collect(Collectors.toList());
        this.contactPhone = entity.getContactPhone();
        this.status = entity.getStatus();
        this.updatedAt = entity.getUpdatedAt();
    }
}
