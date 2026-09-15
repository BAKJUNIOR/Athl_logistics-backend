package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.ServiceDTO;
import athl.logistics.athl_logistics.service.dto.ServiceSummaryDTO;
import athl.logistics.athl_logistics.service.dto.ServiceUpsertDTO;

import java.util.List;

public interface ServiceOfferingService {
    /** Publiés uniquement pour un appelant anonyme, tout (brouillons inclus) pour un admin authentifié. */
    List<ServiceSummaryDTO> list();

    ServiceDTO getById(Long id);

    /** Publié uniquement — page /services/:slug du site vitrine. */
    ServiceDTO getBySlug(String slug);

    ServiceDTO create(ServiceUpsertDTO dto);

    ServiceDTO update(Long id, ServiceUpsertDTO dto);

    ServiceDTO publish(Long id);

    ServiceDTO unpublish(Long id);

    void delete(Long id);
}
