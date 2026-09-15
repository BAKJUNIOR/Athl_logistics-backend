package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.JobBullet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobBulletDTO {
    private String fr;
    private String en;

    public JobBulletDTO(JobBullet entity) {
        this.fr = entity.getFr();
        this.en = entity.getEn();
    }
}
