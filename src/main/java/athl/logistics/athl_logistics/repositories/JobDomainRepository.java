package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.JobDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobDomainRepository extends JpaRepository<JobDomain, Long> {
    boolean existsByLabelFrIgnoreCase(String labelFr);

    Optional<JobDomain> findByLabelFrIgnoreCase(String labelFr);
}
