package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.ServiceOffering;
import athl.logistics.athl_logistics.models.enums.ServiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, Long> {
    boolean existsBySlug(String slug);

    List<ServiceOffering> findByStatus(ServiceStatus status);

    Optional<ServiceOffering> findBySlugAndStatus(String slug, ServiceStatus status);
}
