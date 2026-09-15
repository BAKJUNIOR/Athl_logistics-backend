package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.Project;
import athl.logistics.athl_logistics.models.enums.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String titleFr;
    private String titleEn;
    private String captionFr;
    private String captionEn;
    private String image;
    private boolean featured;
    private boolean wide;
    private ProjectStatus status;
    private Instant updatedAt;

    public ProjectDTO(Project entity) {
        this.id = entity.getId();
        this.titleFr = entity.getTitleFr();
        this.titleEn = entity.getTitleEn();
        this.captionFr = entity.getCaptionFr();
        this.captionEn = entity.getCaptionEn();
        this.image = entity.getImage();
        this.featured = entity.isFeatured();
        this.wide = entity.isWide();
        this.status = entity.getStatus();
        this.updatedAt = entity.getUpdatedAt();
    }
}
