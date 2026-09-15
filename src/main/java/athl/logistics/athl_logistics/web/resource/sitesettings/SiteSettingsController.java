package athl.logistics.athl_logistics.web.resource.sitesettings;

import athl.logistics.athl_logistics.service.SiteSettingsService;
import athl.logistics.athl_logistics.service.dto.HomeStatDTO;
import athl.logistics.athl_logistics.service.dto.SiteContactDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Réglages du site vitrine : compteurs de l'accueil + coordonnées/réseaux sociaux.
// Public en lecture (le front en a besoin pour l'accueil et le footer), modification
// réservée aux admins. Jeux de données fixes : pas de create/delete, un seul PUT global.
@Slf4j
@RestController
@RequiredArgsConstructor
public class SiteSettingsController {

    private final SiteSettingsService siteSettingsService;

    @GetMapping("/api/v1/home-stats")
    public ResponseEntity<List<HomeStatDTO>> listHomeStats() {
        return ResponseEntity.ok(siteSettingsService.listHomeStats());
    }

    @PutMapping("/api/v1/home-stats")
    public ResponseEntity<List<HomeStatDTO>> updateHomeStats(@RequestBody List<HomeStatDTO> stats) {
        log.debug("REST request to update home stats");
        return ResponseEntity.ok(siteSettingsService.updateHomeStats(stats));
    }

    @GetMapping("/api/v1/site-settings/contact")
    public ResponseEntity<SiteContactDTO> getSiteContact() {
        return ResponseEntity.ok(siteSettingsService.getSiteContact());
    }

    @PutMapping("/api/v1/site-settings/contact")
    public ResponseEntity<SiteContactDTO> updateSiteContact(@RequestBody SiteContactDTO dto) {
        log.debug("REST request to update site contact: {}", dto);
        return ResponseEntity.ok(siteSettingsService.updateSiteContact(dto));
    }
}
