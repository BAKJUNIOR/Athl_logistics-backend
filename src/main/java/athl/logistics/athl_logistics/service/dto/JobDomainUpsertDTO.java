package athl.logistics.athl_logistics.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobDomainUpsertDTO {
    @NotBlank(message = "Le libellé en français est requis")
    private String labelFr;

    private String labelEn;
}
