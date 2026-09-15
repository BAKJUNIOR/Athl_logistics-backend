package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import athl.logistics.athl_logistics.service.dto.JobApplicationCreateDTO;
import athl.logistics.athl_logistics.service.dto.JobApplicationDTO;

import java.util.List;

public interface JobApplicationService {
    /** Réservé aux admins : toutes les candidatures, les plus récentes en premier. */
    List<JobApplicationDTO> list();

    /** Soumission publique depuis le formulaire de candidature de la page Carrières. */
    JobApplicationDTO create(JobApplicationCreateDTO dto);

    JobApplicationDTO updateStatus(Long id, SubmissionStatus status);

    void delete(Long id);
}
