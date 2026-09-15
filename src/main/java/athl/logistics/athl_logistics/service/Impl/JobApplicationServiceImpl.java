package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.JobApplication;
import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import athl.logistics.athl_logistics.repositories.JobApplicationRepository;
import athl.logistics.athl_logistics.service.JobApplicationService;
import athl.logistics.athl_logistics.service.dto.JobApplicationCreateDTO;
import athl.logistics.athl_logistics.service.dto.JobApplicationDTO;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationDTO> list() {
        return repository.findAllByOrderByReceivedAtDesc().stream().map(JobApplicationDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobApplicationDTO create(JobApplicationCreateDTO dto) {
        JobApplication entity = new JobApplication();
        entity.setPosition(dto.getPosition());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setExperience(dto.getExperience());
        entity.setCity(dto.getCity());
        entity.setMessage(dto.getMessage());
        entity.setCvUrl(dto.getCvUrl());
        entity.setStatus(SubmissionStatus.NEW);
        JobApplication saved = repository.save(entity);
        log.info("Nouvelle candidature reçue : id={}, poste={}", saved.getId(), saved.getPosition());
        return new JobApplicationDTO(saved);
    }

    @Override
    @Transactional
    public JobApplicationDTO updateStatus(Long id, SubmissionStatus status) {
        JobApplication entity = findEntityOrThrow(id);
        entity.setStatus(status);
        log.info("Statut de la candidature id={} passé à {}", id, status);
        return new JobApplicationDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        JobApplication entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Candidature supprimée : id={}", id);
    }

    private JobApplication findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Candidature introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }
}
