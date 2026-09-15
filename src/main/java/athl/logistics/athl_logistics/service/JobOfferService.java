package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.JobOfferDTO;
import athl.logistics.athl_logistics.service.dto.JobUpsertDTO;

import java.util.List;

public interface JobOfferService {
    /** Publiées uniquement pour un appelant anonyme, tout (brouillons inclus) pour un admin authentifié. */
    List<JobOfferDTO> list();

    JobOfferDTO getById(Long id);

    JobOfferDTO create(JobUpsertDTO dto);

    JobOfferDTO update(Long id, JobUpsertDTO dto);

    JobOfferDTO publish(Long id);

    JobOfferDTO unpublish(Long id);

    void delete(Long id);
}
