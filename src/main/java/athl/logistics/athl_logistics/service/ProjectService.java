package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.ProjectDTO;
import athl.logistics.athl_logistics.service.dto.ProjectUpsertDTO;

import java.util.List;

public interface ProjectService {
    /** Publiés uniquement pour un appelant anonyme, tout (brouillons inclus) pour un admin authentifié. */
    List<ProjectDTO> list();

    ProjectDTO create(ProjectUpsertDTO dto);

    ProjectDTO update(Long id, ProjectUpsertDTO dto);

    ProjectDTO publish(Long id);

    ProjectDTO unpublish(Long id);

    void delete(Long id);
}
