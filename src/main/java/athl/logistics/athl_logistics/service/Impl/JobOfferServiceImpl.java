package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.JobBullet;
import athl.logistics.athl_logistics.models.JobDomain;
import athl.logistics.athl_logistics.models.JobOffer;
import athl.logistics.athl_logistics.models.enums.JobBulletKind;
import athl.logistics.athl_logistics.models.enums.JobStatus;
import athl.logistics.athl_logistics.repositories.JobDomainRepository;
import athl.logistics.athl_logistics.repositories.JobOfferRepository;
import athl.logistics.athl_logistics.service.JobOfferService;
import athl.logistics.athl_logistics.service.dto.JobBulletDTO;
import athl.logistics.athl_logistics.service.dto.JobOfferDTO;
import athl.logistics.athl_logistics.service.dto.JobUpsertDTO;
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
public class JobOfferServiceImpl implements JobOfferService {

    private final JobOfferRepository repository;
    private final JobDomainRepository jobDomainRepository;

    @Override
    @Transactional(readOnly = true)
    public List<JobOfferDTO> list() {
        List<JobOffer> jobs = isAdminRequest() ? repository.findAll() : repository.findByStatus(JobStatus.PUBLISHED);
        return jobs.stream().map(JobOfferDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public JobOfferDTO getById(Long id) {
        return new JobOfferDTO(findEntityOrThrow(id));
    }

    @Override
    @Transactional
    public JobOfferDTO create(JobUpsertDTO dto) {
        JobOffer entity = new JobOffer();
        applyScalarFields(entity, dto);
        applyBullets(entity, dto);

        JobOffer saved = repository.save(entity);
        log.info("Offre d'emploi créée : id={}", saved.getId());
        return new JobOfferDTO(saved);
    }

    @Override
    @Transactional
    public JobOfferDTO update(Long id, JobUpsertDTO dto) {
        JobOffer entity = findEntityOrThrow(id);
        applyScalarFields(entity, dto);
        applyBullets(entity, dto);

        JobOffer saved = repository.save(entity);
        log.info("Offre d'emploi mise à jour : id={}", saved.getId());
        return new JobOfferDTO(saved);
    }

    @Override
    @Transactional
    public JobOfferDTO publish(Long id) {
        JobOffer entity = findEntityOrThrow(id);
        entity.setStatus(JobStatus.PUBLISHED);
        return new JobOfferDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public JobOfferDTO unpublish(Long id) {
        JobOffer entity = findEntityOrThrow(id);
        entity.setStatus(JobStatus.DRAFT);
        return new JobOfferDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        JobOffer entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Offre d'emploi supprimée : id={}", id);
    }

    private JobOffer findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Offre introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }

    private void applyScalarFields(JobOffer entity, JobUpsertDTO dto) {
        JobDomain domain = jobDomainRepository.findById(dto.getDomainId())
                .orElseThrow(() -> new AccountResourceException("Domaine introuvable avec l'ID : " + dto.getDomainId(), HttpStatus.NOT_FOUND));
        entity.setDomain(domain);
        entity.setTitleFr(dto.getTitleFr());
        entity.setTitleEn(dto.getTitleEn());
        entity.setDescriptionFr(dto.getDescriptionFr());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setMetaFr(dto.getMetaFr());
        entity.setMetaEn(dto.getMetaEn());
        entity.setPublishedAt(dto.getPublishedAt());
        entity.setDeadline(dto.getDeadline());
        entity.setContactPhone(dto.getContactPhone());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : JobStatus.DRAFT);
    }

    private void applyBullets(JobOffer entity, JobUpsertDTO dto) {
        entity.getBullets().clear();
        addBullets(entity, dto.getMissions(), JobBulletKind.MISSION);
        addBullets(entity, dto.getProfile(), JobBulletKind.PROFILE);
    }

    private void addBullets(JobOffer entity, List<JobBulletDTO> dtos, JobBulletKind kind) {
        if (dtos == null) return;
        int order = 0;
        for (JobBulletDTO d : dtos) {
            JobBullet b = new JobBullet();
            b.setJob(entity);
            b.setKind(kind);
            b.setFr(d.getFr());
            b.setEn(d.getEn());
            b.setSortOrder(order++);
            entity.getBullets().add(b);
        }
    }

    private boolean isAdminRequest() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals("ROLE_SUPER_ADMIN") || a.equals("ROLE_ADMIN"));
    }
}
