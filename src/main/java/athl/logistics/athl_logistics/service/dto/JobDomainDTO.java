package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.JobDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobDomainDTO {
    private Long id;
    private String labelFr;
    private String labelEn;

    public JobDomainDTO(JobDomain entity) {
        this.id = entity.getId();
        this.labelFr = entity.getLabelFr();
        this.labelEn = entity.getLabelEn();
    }
}
