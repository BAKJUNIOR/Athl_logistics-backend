package athl.logistics.athl_logistics.web.resource.jobs;

import athl.logistics.athl_logistics.service.JobOfferService;
import athl.logistics.athl_logistics.service.dto.JobOfferDTO;
import athl.logistics.athl_logistics.service.dto.JobUpsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobOfferController {

    private final JobOfferService jobOfferService;

    // Public : publiées uniquement pour un appelant anonyme, tout pour un admin (voir SecurityConfig).
    // Le front charge toutes les offres (missions/profil compris) en un seul appel — pas de page
    // détail séparée sur /carrieres, contrairement aux services.
    @GetMapping
    public ResponseEntity<List<JobOfferDTO>> list() {
        return ResponseEntity.ok(jobOfferService.list());
    }

    // Admin uniquement : formulaire d'édition du BO (tous statuts).
    @GetMapping("/{id}")
    public ResponseEntity<JobOfferDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(jobOfferService.getById(id));
    }

    @PostMapping
    public ResponseEntity<JobOfferDTO> create(@Valid @RequestBody JobUpsertDTO dto) {
        log.debug("REST request to create a job offer: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JobOfferDTO> update(@PathVariable Long id, @Valid @RequestBody JobUpsertDTO dto) {
        log.debug("REST request to update job offer ID: {}", id);
        return ResponseEntity.ok(jobOfferService.update(id, dto));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<JobOfferDTO> publish(@PathVariable Long id) {
        log.debug("REST request to publish job offer ID: {}", id);
        return ResponseEntity.ok(jobOfferService.publish(id));
    }

    @PostMapping("/{id}/unpublish")
    public ResponseEntity<JobOfferDTO> unpublish(@PathVariable Long id) {
        log.debug("REST request to unpublish job offer ID: {}", id);
        return ResponseEntity.ok(jobOfferService.unpublish(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete job offer ID: {}", id);
        jobOfferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
