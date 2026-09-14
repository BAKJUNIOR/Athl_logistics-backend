package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.UserValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserValidationCodeRepository extends JpaRepository<UserValidationCode, Long> {
    Optional<UserValidationCode> findByCode(String code);
    void deleteByUserId(Long id);

    Optional<UserValidationCode> findByUserId(Long id);
}
