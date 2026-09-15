package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.JobOffer;
import athl.logistics.athl_logistics.models.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    List<JobOffer> findByStatus(JobStatus status);

    boolean existsByDomain_Id(Long domainId);
}
