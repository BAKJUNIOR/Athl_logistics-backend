package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.HomeStat;
import athl.logistics.athl_logistics.models.enums.HomeStatKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeStatRepository extends JpaRepository<HomeStat, HomeStatKey> {
}
