package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import athl.logistics.athl_logistics.service.dto.QuoteRequestCreateDTO;
import athl.logistics.athl_logistics.service.dto.QuoteRequestDTO;

import java.util.List;

public interface QuoteRequestService {
    /** Réservé aux admins : toutes les demandes, les plus récentes en premier. */
    List<QuoteRequestDTO> list();

    /** Soumission publique depuis le formulaire "Demander un devis" du site vitrine. */
    QuoteRequestDTO create(QuoteRequestCreateDTO dto);

    QuoteRequestDTO updateStatus(Long id, SubmissionStatus status);

    void delete(Long id);
}
