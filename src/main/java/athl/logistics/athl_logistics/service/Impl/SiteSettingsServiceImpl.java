package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.HomeStat;
import athl.logistics.athl_logistics.models.SiteContact;
import athl.logistics.athl_logistics.repositories.HomeStatRepository;
import athl.logistics.athl_logistics.repositories.SiteContactRepository;
import athl.logistics.athl_logistics.service.SiteSettingsService;
import athl.logistics.athl_logistics.service.dto.HomeStatDTO;
import athl.logistics.athl_logistics.service.dto.SiteContactDTO;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SiteSettingsServiceImpl implements SiteSettingsService {

    private static final Long SITE_CONTACT_ID = 1L;

    private final HomeStatRepository homeStatRepository;
    private final SiteContactRepository siteContactRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HomeStatDTO> listHomeStats() {
        return homeStatRepository.findAll().stream()
                .sorted(Comparator.comparing(s -> s.getKey().name()))
                .map(HomeStatDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeStatDTO> updateHomeStats(List<HomeStatDTO> stats) {
        for (HomeStatDTO dto : stats) {
            HomeStat entity = homeStatRepository.findById(dto.getKey())
                    .orElseThrow(() -> new AccountResourceException("Compteur inconnu : " + dto.getKey(), HttpStatus.NOT_FOUND));
            entity.setValue(dto.getValue());
            entity.setDecimals(dto.getDecimals());
            entity.setSuffix(dto.getSuffix());
            homeStatRepository.save(entity);
        }
        log.info("Compteurs de l'accueil mis à jour");
        return listHomeStats();
    }

    @Override
    @Transactional(readOnly = true)
    public SiteContactDTO getSiteContact() {
        return new SiteContactDTO(findContactOrThrow());
    }

    @Override
    @Transactional
    public SiteContactDTO updateSiteContact(SiteContactDTO dto) {
        SiteContact entity = findContactOrThrow();
        entity.setPhone1(dto.getPhone1());
        entity.setPhone2(dto.getPhone2());
        entity.setPhone3(dto.getPhone3());
        entity.setAddress(dto.getAddress());
        entity.setFacebookUrl(dto.getFacebookUrl());
        entity.setYoutubeUrl(dto.getYoutubeUrl());
        entity.setInstagramUrl(dto.getInstagramUrl());
        entity.setLinkedinUrl(dto.getLinkedinUrl());
        SiteContact saved = siteContactRepository.save(entity);
        log.info("Coordonnées du site mises à jour");
        return new SiteContactDTO(saved);
    }

    private SiteContact findContactOrThrow() {
        return siteContactRepository.findById(SITE_CONTACT_ID)
                .orElseThrow(() -> new AccountResourceException("Coordonnées du site introuvables.", HttpStatus.NOT_FOUND));
    }
}
