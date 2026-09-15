package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.ServicePrestation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePrestationDTO {
    private String titleFr;
    private String titleEn;
    private String descriptionFr;
    private String descriptionEn;

    public ServicePrestationDTO(ServicePrestation entity) {
        this.titleFr = entity.getTitleFr();
        this.titleEn = entity.getTitleEn();
        this.descriptionFr = entity.getDescriptionFr();
        this.descriptionEn = entity.getDescriptionEn();
    }
}
