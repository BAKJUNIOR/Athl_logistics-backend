package athl.logistics.athl_logistics.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/** Soumission publique du formulaire "Demander un devis" du site vitrine. */
@Data
@NoArgsConstructor
public class QuoteRequestCreateDTO {
    @NotBlank(message = "Le service est requis")
    private String serviceLabel;

    @NotBlank(message = "Le nom est requis")
    private String name;

    @NotBlank(message = "Le téléphone est requis")
    private String phone;

    @NotBlank(message = "La description est requise")
    private String description;

    private List<String> attachments;
}
