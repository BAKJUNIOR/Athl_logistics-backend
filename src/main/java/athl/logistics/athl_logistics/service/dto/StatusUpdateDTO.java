package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Changement de statut d'une soumission (devis ou candidature), fait par un admin depuis le BO. */
@Data
@NoArgsConstructor
public class StatusUpdateDTO {
    @NotNull(message = "Le statut est requis")
    private SubmissionStatus status;
}
