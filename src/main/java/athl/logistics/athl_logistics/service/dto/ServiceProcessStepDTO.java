package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.ServiceProcessStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProcessStepDTO {
    private String number;
    private String titleFr;
    private String titleEn;
    private String descriptionFr;
    private String descriptionEn;

    public ServiceProcessStepDTO(ServiceProcessStep entity) {
        this.number = entity.getNumber();
        this.titleFr = entity.getTitleFr();
        this.titleEn = entity.getTitleEn();
        this.descriptionFr = entity.getDescriptionFr();
        this.descriptionEn = entity.getDescriptionEn();
    }
}
