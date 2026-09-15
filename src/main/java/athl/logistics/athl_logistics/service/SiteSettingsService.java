package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.HomeStatDTO;
import athl.logistics.athl_logistics.service.dto.SiteContactDTO;

import java.util.List;

public interface SiteSettingsService {
    List<HomeStatDTO> listHomeStats();

    List<HomeStatDTO> updateHomeStats(List<HomeStatDTO> stats);

    SiteContactDTO getSiteContact();

    SiteContactDTO updateSiteContact(SiteContactDTO dto);
}
