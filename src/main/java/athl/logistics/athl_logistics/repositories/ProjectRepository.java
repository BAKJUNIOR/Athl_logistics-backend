package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.Project;
import athl.logistics.athl_logistics.models.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);
}
