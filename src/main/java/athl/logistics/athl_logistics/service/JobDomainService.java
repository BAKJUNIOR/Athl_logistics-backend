package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.JobDomainDTO;
import athl.logistics.athl_logistics.service.dto.JobDomainUpsertDTO;

import java.util.List;

public interface JobDomainService {
    List<JobDomainDTO> list();

    JobDomainDTO create(JobDomainUpsertDTO dto);

    JobDomainDTO update(Long id, JobDomainUpsertDTO dto);

    void delete(Long id);
}
