package athl.logistics.athl_logistics.web.resource.services;

import athl.logistics.athl_logistics.service.ServiceOfferingService;
import athl.logistics.athl_logistics.service.dto.ServiceDTO;
import athl.logistics.athl_logistics.service.dto.ServiceSummaryDTO;
import athl.logistics.athl_logistics.service.dto.ServiceUpsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    // Public : publiés uniquement pour un appelant anonyme, tout pour un admin (voir SecurityConfig).
    @GetMapping
    public ResponseEntity<List<ServiceSummaryDTO>> list() {
        return ResponseEntity.ok(serviceOfferingService.list());
    }

    // Admin uniquement : formulaire d'édition du BO (accès par id, tous statuts).
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceOfferingService.getById(id));
    }

    // Public : page /services/:slug du site vitrine (publiés uniquement).
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ServiceDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(serviceOfferingService.getBySlug(slug));
    }

    @PostMapping
    public ResponseEntity<ServiceDTO> create(@Valid @RequestBody ServiceUpsertDTO dto) {
        log.debug("REST request to create a service: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceOfferingService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServiceDTO> update(@PathVariable Long id, @Valid @RequestBody ServiceUpsertDTO dto) {
        log.debug("REST request to update service ID: {}", id);
        return ResponseEntity.ok(serviceOfferingService.update(id, dto));
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<ServiceDTO> publish(@PathVariable Long id) {
        log.debug("REST request to publish service ID: {}", id);
        return ResponseEntity.ok(serviceOfferingService.publish(id));
    }

    @PostMapping("/{id}/unpublish")
    public ResponseEntity<ServiceDTO> unpublish(@PathVariable Long id) {
        log.debug("REST request to unpublish service ID: {}", id);
        return ResponseEntity.ok(serviceOfferingService.unpublish(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete service ID: {}", id);
        serviceOfferingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
