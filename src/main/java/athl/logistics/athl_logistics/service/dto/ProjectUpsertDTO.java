package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.enums.ProjectStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectUpsertDTO {
    @NotBlank(message = "Le titre en français est requis")
    private String titleFr;

    private String titleEn;
    private String captionFr;
    private String captionEn;
    private String image;
    private boolean featured;
    private boolean wide;
    private ProjectStatus status = ProjectStatus.DRAFT;
}
