package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.PopupDTO;
import athl.logistics.athl_logistics.service.dto.PopupUpsertDTO;

import java.util.List;

public interface PopupService {
    /** Actives uniquement pour un appelant anonyme, tout (inactives incluses) pour un admin. */
    List<PopupDTO> list();

    PopupDTO getById(Long id);

    PopupDTO create(PopupUpsertDTO dto);

    PopupDTO update(Long id, PopupUpsertDTO dto);

    /** Active cette popup et désactive automatiquement toute autre popup active sur la même page. */
    PopupDTO activate(Long id);

    PopupDTO deactivate(Long id);

    void delete(Long id);
}
