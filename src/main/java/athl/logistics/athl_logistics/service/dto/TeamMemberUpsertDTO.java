package athl.logistics.athl_logistics.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamMemberUpsertDTO {
    @NotBlank(message = "Le nom est requis")
    private String name;

    @NotBlank(message = "Le rôle en français est requis")
    private String roleFr;

    private String roleEn;
    private String photo;
    private int sortOrder;
}
