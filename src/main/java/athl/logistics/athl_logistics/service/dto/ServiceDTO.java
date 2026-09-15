package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.ServiceOffering;
import athl.logistics.athl_logistics.models.enums.ServiceStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

// Détail complet d'un service — édition (BO) et page /services/:slug (front).
@Data
@NoArgsConstructor
public class ServiceDTO {
    private Long id;
    private String slug;
    private String number;
    private String titleFr;
    private String titleEn;
    private String shortTitleFr;
    private String shortTitleEn;
    private String leadFr;
    private String leadEn;
    private String image;
    private String heroImage;
    private List<String> gallery;
    private List<ServicePrestationDTO> prestations;
    private List<ServiceProcessStepDTO> process;
    private ServiceStatus status;
    private Instant updatedAt;

    public ServiceDTO(ServiceOffering entity) {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.number = entity.getNumber();
        this.titleFr = entity.getTitleFr();
        this.titleEn = entity.getTitleEn();
        this.shortTitleFr = entity.getShortTitleFr();
        this.shortTitleEn = entity.getShortTitleEn();
        this.leadFr = entity.getLeadFr();
        this.leadEn = entity.getLeadEn();
        this.image = entity.getImage();
        this.heroImage = entity.getHeroImage();
        this.gallery = entity.getGallery();
        this.prestations = entity.getPrestations().stream().map(ServicePrestationDTO::new).collect(Collectors.toList());
        this.process = entity.getProcessSteps().stream().map(ServiceProcessStepDTO::new).collect(Collectors.toList());
        this.status = entity.getStatus();
        this.updatedAt = entity.getUpdatedAt();
    }
}
