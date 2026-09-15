package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.ServiceOffering;
import athl.logistics.athl_logistics.models.ServicePrestation;
import athl.logistics.athl_logistics.models.ServiceProcessStep;
import athl.logistics.athl_logistics.models.enums.ServiceStatus;
import athl.logistics.athl_logistics.repositories.ServiceOfferingRepository;
import athl.logistics.athl_logistics.service.ServiceOfferingService;
import athl.logistics.athl_logistics.service.dto.ServiceDTO;
import athl.logistics.athl_logistics.service.dto.ServicePrestationDTO;
import athl.logistics.athl_logistics.service.dto.ServiceProcessStepDTO;
import athl.logistics.athl_logistics.service.dto.ServiceSummaryDTO;
import athl.logistics.athl_logistics.service.dto.ServiceUpsertDTO;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import com.github.slugify.Slugify;
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
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository repository;
    private final Slugify slugify = Slugify.builder().build();

    @Override
    @Transactional(readOnly = true)
    public List<ServiceSummaryDTO> list() {
        List<ServiceOffering> services = isAdminRequest()
                ? repository.findAll()
                : repository.findByStatus(ServiceStatus.PUBLISHED);
        return services.stream().map(ServiceSummaryDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceDTO getById(Long id) {
        return new ServiceDTO(findEntityOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ServiceDTO getBySlug(String slug) {
        ServiceOffering entity = repository.findBySlugAndStatus(slug, ServiceStatus.PUBLISHED)
                .orElseThrow(() -> new AccountResourceException("Service introuvable.", HttpStatus.NOT_FOUND));
        return new ServiceDTO(entity);
    }

    @Override
    @Transactional
    public ServiceDTO create(ServiceUpsertDTO dto) {
        ServiceOffering entity = new ServiceOffering();
        applyScalarFields(entity, dto);
        String slugSource = (dto.getShortTitleFr() != null && !dto.getShortTitleFr().isBlank())
                ? dto.getShortTitleFr()
                : dto.getTitleFr();
        entity.setSlug(generateUniqueSlug(slugSource));
        applyPrestations(entity, dto.getPrestations());
        applyProcessSteps(entity, dto.getProcess());

        ServiceOffering saved = repository.save(entity);
        log.info("Service créé : id={}, slug={}", saved.getId(), saved.getSlug());
        return new ServiceDTO(saved);
    }

    @Override
    @Transactional
    public ServiceDTO update(Long id, ServiceUpsertDTO dto) {
        ServiceOffering entity = findEntityOrThrow(id);
        applyScalarFields(entity, dto);
        // Le slug est figé à la création et n'est jamais régénéré, même si le titre change,
        // pour ne pas casser les liens déjà partagés d'un service publié.
        applyPrestations(entity, dto.getPrestations());
        applyProcessSteps(entity, dto.getProcess());

        ServiceOffering saved = repository.save(entity);
        log.info("Service mis à jour : id={}", saved.getId());
        return new ServiceDTO(saved);
    }

    @Override
    @Transactional
    public ServiceDTO publish(Long id) {
        ServiceOffering entity = findEntityOrThrow(id);
        entity.setStatus(ServiceStatus.PUBLISHED);
        return new ServiceDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public ServiceDTO unpublish(Long id) {
        ServiceOffering entity = findEntityOrThrow(id);
        entity.setStatus(ServiceStatus.DRAFT);
        return new ServiceDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ServiceOffering entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Service supprimé : id={}", id);
    }

    private ServiceOffering findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Service introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }

    private void applyScalarFields(ServiceOffering entity, ServiceUpsertDTO dto) {
        entity.setNumber(dto.getNumber());
        entity.setTitleFr(dto.getTitleFr());
        entity.setTitleEn(dto.getTitleEn());
        entity.setShortTitleFr(dto.getShortTitleFr());
        entity.setShortTitleEn(dto.getShortTitleEn());
        entity.setLeadFr(dto.getLeadFr());
        entity.setLeadEn(dto.getLeadEn());
        entity.setImage(dto.getImage());
        entity.setHeroImage(dto.getHeroImage());
        entity.getGallery().clear();
        if (dto.getGallery() != null) {
            entity.getGallery().addAll(dto.getGallery());
        }
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : ServiceStatus.DRAFT);
    }

    private void applyPrestations(ServiceOffering entity, List<ServicePrestationDTO> dtos) {
        entity.getPrestations().clear();
        if (dtos == null) return;
        int order = 0;
        for (ServicePrestationDTO d : dtos) {
            ServicePrestation p = new ServicePrestation();
            p.setService(entity);
            p.setTitleFr(d.getTitleFr());
            p.setTitleEn(d.getTitleEn());
            p.setDescriptionFr(d.getDescriptionFr());
            p.setDescriptionEn(d.getDescriptionEn());
            p.setSortOrder(order++);
            entity.getPrestations().add(p);
        }
    }

    private void applyProcessSteps(ServiceOffering entity, List<ServiceProcessStepDTO> dtos) {
        entity.getProcessSteps().clear();
        if (dtos == null) return;
        int order = 0;
        for (ServiceProcessStepDTO d : dtos) {
            ServiceProcessStep s = new ServiceProcessStep();
            s.setService(entity);
            s.setNumber(d.getNumber());
            s.setTitleFr(d.getTitleFr());
            s.setTitleEn(d.getTitleEn());
            s.setDescriptionFr(d.getDescriptionFr());
            s.setDescriptionEn(d.getDescriptionEn());
            s.setSortOrder(order++);
            entity.getProcessSteps().add(s);
        }
    }

    /**
     * Slug propre (sans suffixe aléatoire, contrairement à SlugifyUtils) — unique en base,
     * avec suffixe numérique en cas de collision (ex: "construction", puis "construction-2").
     */
    private String generateUniqueSlug(String titleFr) {
        String base = slugify.slugify(titleFr);
        String candidate = base;
        int suffix = 2;
        while (repository.existsBySlug(candidate)) {
            candidate = base + "-" + suffix;
            suffix++;
        }
        return candidate;
    }

    private boolean isAdminRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_SUPER_ADMIN") || a.equals("ROLE_ADMIN"));
    }
}
