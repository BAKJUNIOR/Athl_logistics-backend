package athl.logistics.athl_logistics.web.resource.projects;

import athl.logistics.athl_logistics.service.ProjectService;
import athl.logistics.athl_logistics.service.dto.ProjectDTO;
import athl.logistics.athl_logistics.service.dto.ProjectUpsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Public : publiés uniquement pour un appelant anonyme, tout pour un admin (voir SecurityConfig).
@Slf4j
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> list() {
        return ResponseEntity.ok(projectService.list());
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> create(@Valid @RequestBody ProjectUpsertDTO dto) {
        log.debug("REST request to create a project: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @Valid @RequestBody ProjectUpsertDTO dto) {
        log.debug("REST request to update project ID: {}", id);
        return ResponseEntity.ok(projectService.update(id, dto));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<ProjectDTO> publish(@PathVariable Long id) {
        log.debug("REST request to publish project ID: {}", id);
        return ResponseEntity.ok(projectService.publish(id));
    }

    @PostMapping("/{id}/unpublish")
    public ResponseEntity<ProjectDTO> unpublish(@PathVariable Long id) {
        log.debug("REST request to unpublish project ID: {}", id);
        return ResponseEntity.ok(projectService.unpublish(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete project ID: {}", id);
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
