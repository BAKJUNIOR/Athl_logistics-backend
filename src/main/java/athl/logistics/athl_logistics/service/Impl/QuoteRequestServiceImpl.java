package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.QuoteRequest;
import athl.logistics.athl_logistics.models.enums.SubmissionStatus;
import athl.logistics.athl_logistics.repositories.QuoteRequestRepository;
import athl.logistics.athl_logistics.service.QuoteRequestService;
import athl.logistics.athl_logistics.service.dto.QuoteRequestCreateDTO;
import athl.logistics.athl_logistics.service.dto.QuoteRequestDTO;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuoteRequestServiceImpl implements QuoteRequestService {

    private final QuoteRequestRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<QuoteRequestDTO> list() {
        return repository.findAllByOrderByReceivedAtDesc().stream().map(QuoteRequestDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuoteRequestDTO create(QuoteRequestCreateDTO dto) {
        QuoteRequest entity = new QuoteRequest();
        entity.setServiceLabel(dto.getServiceLabel());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setDescription(dto.getDescription());
        entity.setAttachments(dto.getAttachments() != null ? new ArrayList<>(dto.getAttachments()) : new ArrayList<>());
        entity.setStatus(SubmissionStatus.NEW);
        QuoteRequest saved = repository.save(entity);
        log.info("Nouvelle demande de devis reçue : id={}, service={}", saved.getId(), saved.getServiceLabel());
        return new QuoteRequestDTO(saved);
    }

    @Override
    @Transactional
    public QuoteRequestDTO updateStatus(Long id, SubmissionStatus status) {
        QuoteRequest entity = findEntityOrThrow(id);
        entity.setStatus(status);
        log.info("Statut de la demande de devis id={} passé à {}", id, status);
        return new QuoteRequestDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        QuoteRequest entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Demande de devis supprimée : id={}", id);
    }

    private QuoteRequest findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Demande de devis introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }
}
