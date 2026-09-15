package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.JobDomain;
import athl.logistics.athl_logistics.repositories.JobDomainRepository;
import athl.logistics.athl_logistics.repositories.JobOfferRepository;
import athl.logistics.athl_logistics.service.JobDomainService;
import athl.logistics.athl_logistics.service.dto.JobDomainDTO;
import athl.logistics.athl_logistics.service.dto.JobDomainUpsertDTO;
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
public class JobDomainServiceImpl implements JobDomainService {

    private final JobDomainRepository repository;
    private final JobOfferRepository jobOfferRepository;

    @Override
    @Transactional(readOnly = true)
    public List<JobDomainDTO> list() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(JobDomain::getLabelFr))
                .map(JobDomainDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobDomainDTO create(JobDomainUpsertDTO dto) {
        if (repository.existsByLabelFrIgnoreCase(dto.getLabelFr())) {
            throw new AccountResourceException("Ce domaine existe déjà.");
        }
        JobDomain entity = new JobDomain();
        entity.setLabelFr(dto.getLabelFr());
        entity.setLabelEn(dto.getLabelEn());
        JobDomain saved = repository.save(entity);
        log.info("Domaine de carrière créé : id={}, label={}", saved.getId(), saved.getLabelFr());
        return new JobDomainDTO(saved);
    }

    @Override
    @Transactional
    public JobDomainDTO update(Long id, JobDomainUpsertDTO dto) {
        JobDomain entity = repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Domaine introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
        if (!entity.getLabelFr().equalsIgnoreCase(dto.getLabelFr()) && repository.existsByLabelFrIgnoreCase(dto.getLabelFr())) {
            throw new AccountResourceException("Ce domaine existe déjà.");
        }
        entity.setLabelFr(dto.getLabelFr());
        entity.setLabelEn(dto.getLabelEn());
        JobDomain saved = repository.save(entity);
        log.info("Domaine de carrière mis à jour : id={}", saved.getId());
        return new JobDomainDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        JobDomain entity = repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Domaine introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
        if (jobOfferRepository.existsByDomain_Id(id)) {
            throw new AccountResourceException("Ce domaine est utilisé par au moins une offre d'emploi et ne peut pas être supprimé.");
        }
        repository.delete(entity);
        log.info("Domaine de carrière supprimé : id={}", id);
    }
}
