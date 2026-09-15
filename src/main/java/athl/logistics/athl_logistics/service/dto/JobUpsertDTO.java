package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.enums.JobStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class JobUpsertDTO {

    @NotNull(message = "Le domaine est requis")
    private Long domainId;

    @NotBlank(message = "Le titre en français est requis")
    private String titleFr;

    private String titleEn;
    private String descriptionFr;
    private String descriptionEn;
    private String metaFr;
    private String metaEn;
    private LocalDate publishedAt;

    @NotNull(message = "La date de clôture est requise")
    private LocalDate deadline;

    private List<JobBulletDTO> missions = new ArrayList<>();
    private List<JobBulletDTO> profile = new ArrayList<>();
    private String contactPhone;
    private JobStatus status = JobStatus.DRAFT;
}
