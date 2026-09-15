package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findAllByOrderByReceivedAtDesc();
}
