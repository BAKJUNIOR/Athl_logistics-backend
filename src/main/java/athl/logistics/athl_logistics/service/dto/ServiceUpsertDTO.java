package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.enums.ServiceStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Payload créer/modifier un service — pas de slug ni d'id : le slug est généré une seule fois
// par le backend à la création et n'est plus jamais modifiable depuis le BO (voir ServiceOffering).
@Data
@NoArgsConstructor
public class ServiceUpsertDTO {

    private String number;

    @NotBlank(message = "Le titre en français est requis")
    private String titleFr;

    private String titleEn;
    private String shortTitleFr;
    private String shortTitleEn;

    @NotBlank(message = "Le texte de présentation en français est requis")
    private String leadFr;

    private String leadEn;
    private String image;
    private String heroImage;
    private List<String> gallery = new ArrayList<>();
    private List<ServicePrestationDTO> prestations = new ArrayList<>();
    private List<ServiceProcessStepDTO> process = new ArrayList<>();
    private ServiceStatus status = ServiceStatus.DRAFT;
}
