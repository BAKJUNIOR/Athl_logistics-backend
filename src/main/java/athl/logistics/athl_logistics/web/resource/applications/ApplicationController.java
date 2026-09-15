package athl.logistics.athl_logistics.web.resource.applications;

import athl.logistics.athl_logistics.service.JobApplicationService;
import athl.logistics.athl_logistics.service.dto.JobApplicationCreateDTO;
import athl.logistics.athl_logistics.service.dto.JobApplicationDTO;
import athl.logistics.athl_logistics.service.dto.StatusUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Création publique (formulaire de candidature de la page Carrières), consultation/suivi réservés aux admins.
@Slf4j
@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final JobApplicationService jobApplicationService;

    @GetMapping
    public ResponseEntity<List<JobApplicationDTO>> list() {
        return ResponseEntity.ok(jobApplicationService.list());
    }

    @PostMapping
    public ResponseEntity<JobApplicationDTO> create(@Valid @RequestBody JobApplicationCreateDTO dto) {
        log.debug("REST request to create a job application: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationService.create(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobApplicationDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDTO dto) {
        log.debug("REST request to update job application status ID: {}", id);
        return ResponseEntity.ok(jobApplicationService.updateStatus(id, dto.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete job application ID: {}", id);
        jobApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
