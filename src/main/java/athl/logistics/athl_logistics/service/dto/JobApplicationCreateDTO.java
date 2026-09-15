package athl.logistics.athl_logistics.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Soumission publique du formulaire de candidature de la page Carrières. */
@Data
@NoArgsConstructor
public class JobApplicationCreateDTO {
    @NotBlank(message = "Le poste est requis")
    private String position;

    @NotBlank(message = "Le nom est requis")
    private String name;

    @NotBlank(message = "Le téléphone est requis")
    private String phone;

    private String email;
    private String experience;
    private String city;
    private String message;
    private String cvUrl;
}
