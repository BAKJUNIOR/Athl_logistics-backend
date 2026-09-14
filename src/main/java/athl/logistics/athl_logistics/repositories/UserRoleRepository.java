package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.UserRole;
import athl.logistics.athl_logistics.models.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(RoleName roleName);
    boolean existsByRoleName(RoleName roleName);
}
