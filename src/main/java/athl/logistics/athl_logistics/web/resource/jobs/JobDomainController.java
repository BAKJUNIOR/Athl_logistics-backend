package athl.logistics.athl_logistics.web.resource.jobs;

import athl.logistics.athl_logistics.service.JobDomainService;
import athl.logistics.athl_logistics.service.dto.JobDomainDTO;
import athl.logistics.athl_logistics.service.dto.JobDomainUpsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/job-domains")
@RequiredArgsConstructor
public class JobDomainController {

    private final JobDomainService jobDomainService;

    @GetMapping
    public ResponseEntity<List<JobDomainDTO>> list() {
        return ResponseEntity.ok(jobDomainService.list());
    }

    @PostMapping
    public ResponseEntity<JobDomainDTO> create(@Valid @RequestBody JobDomainUpsertDTO dto) {
        log.debug("REST request to create a job domain: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobDomainService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<JobDomainDTO> update(@PathVariable Long id, @Valid @RequestBody JobDomainUpsertDTO dto) {
        log.debug("REST request to update job domain ID: {}", id);
        return ResponseEntity.ok(jobDomainService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete job domain ID: {}", id);
        jobDomainService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
