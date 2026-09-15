package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.Popup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupRepository extends JpaRepository<Popup, Long> {
    List<Popup> findByActiveTrue();

    List<Popup> findByPageAndActiveTrueAndIdNot(String page, Long id);
}
