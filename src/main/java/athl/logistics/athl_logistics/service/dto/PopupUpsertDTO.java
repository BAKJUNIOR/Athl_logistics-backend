package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.enums.PopupFrequency;
import athl.logistics.athl_logistics.models.enums.PopupLayout;
import athl.logistics.athl_logistics.models.enums.PopupType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopupUpsertDTO {
    @NotNull(message = "La page cible est requise")
    private String page = "";

    private boolean active;

    @NotNull(message = "Le type est requis")
    private PopupType type;

    private PopupLayout layout = PopupLayout.STACKED;
    private PopupFrequency frequency = PopupFrequency.ONCE_PER_VISITOR;
    private int delayMs = 1200;
    private String eyebrow;

    @NotBlank(message = "Le titre est requis")
    private String title;

    private String text;
    private String image;
    private String video;
    private boolean collectEmail;
    private String ctaLabel;
    private String ctaUrl;
}
