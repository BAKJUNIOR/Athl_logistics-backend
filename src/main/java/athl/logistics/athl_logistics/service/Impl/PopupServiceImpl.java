package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.Popup;
import athl.logistics.athl_logistics.repositories.PopupRepository;
import athl.logistics.athl_logistics.service.PopupService;
import athl.logistics.athl_logistics.service.dto.PopupDTO;
import athl.logistics.athl_logistics.service.dto.PopupUpsertDTO;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PopupServiceImpl implements PopupService {

    private final PopupRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<PopupDTO> list() {
        List<Popup> popups = isAdminRequest() ? repository.findAll() : repository.findByActiveTrue();
        return popups.stream().map(PopupDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PopupDTO getById(Long id) {
        return new PopupDTO(findEntityOrThrow(id));
    }

    @Override
    @Transactional
    public PopupDTO create(PopupUpsertDTO dto) {
        Popup entity = new Popup();
        applyFields(entity, dto);
        Popup saved = repository.save(entity);
        if (saved.isActive()) {
            deactivateSiblings(saved.getPage(), saved.getId());
        }
        log.info("Popup créée : id={}", saved.getId());
        return new PopupDTO(saved);
    }

    @Override
    @Transactional
    public PopupDTO update(Long id, PopupUpsertDTO dto) {
        Popup entity = findEntityOrThrow(id);
        applyFields(entity, dto);
        Popup saved = repository.save(entity);
        if (saved.isActive()) {
            deactivateSiblings(saved.getPage(), saved.getId());
        }
        log.info("Popup mise à jour : id={}", saved.getId());
        return new PopupDTO(saved);
    }

    @Override
    @Transactional
    public PopupDTO activate(Long id) {
        Popup entity = findEntityOrThrow(id);
        entity.setActive(true);
        Popup saved = repository.save(entity);
        deactivateSiblings(saved.getPage(), saved.getId());
        log.info("Popup activée : id={}, page={}", id, saved.getPage());
        return new PopupDTO(saved);
    }

    @Override
    @Transactional
    public PopupDTO deactivate(Long id) {
        Popup entity = findEntityOrThrow(id);
        entity.setActive(false);
        return new PopupDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Popup entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Popup supprimée : id={}", id);
    }

    /** Une seule popup active par page à la fois : on désactive les autres qui le seraient encore. */
    private void deactivateSiblings(String page, Long keepActiveId) {
        List<Popup> siblings = repository.findByPageAndActiveTrueAndIdNot(page, keepActiveId);
        for (Popup sibling : siblings) {
            sibling.setActive(false);
            repository.save(sibling);
            log.info("Popup désactivée automatiquement (une seule active par page) : id={}", sibling.getId());
        }
    }

    private Popup findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Popup introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }

    private void applyFields(Popup entity, PopupUpsertDTO dto) {
        entity.setPage(dto.getPage() != null ? dto.getPage() : "");
        entity.setActive(dto.isActive());
        entity.setType(dto.getType());
        entity.setLayout(dto.getLayout());
        entity.setFrequency(dto.getFrequency());
        entity.setDelayMs(dto.getDelayMs());
        entity.setEyebrow(dto.getEyebrow());
        entity.setTitle(dto.getTitle());
        entity.setText(dto.getText());
        entity.setImage(dto.getImage());
        entity.setVideo(dto.getVideo());
        entity.setCollectEmail(dto.isCollectEmail());
        entity.setCtaLabel(dto.getCtaLabel());
        entity.setCtaUrl(dto.getCtaUrl());
    }

    private boolean isAdminRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_SUPER_ADMIN") || a.equals("ROLE_ADMIN"));
    }
}
