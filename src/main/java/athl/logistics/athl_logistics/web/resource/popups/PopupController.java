package athl.logistics.athl_logistics.web.resource.popups;

import athl.logistics.athl_logistics.service.PopupService;
import athl.logistics.athl_logistics.service.dto.PopupDTO;
import athl.logistics.athl_logistics.service.dto.PopupUpsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Public : actives uniquement pour un appelant anonyme, tout pour un admin (voir SecurityConfig).
@Slf4j
@RestController
@RequestMapping("/api/v1/popups")
@RequiredArgsConstructor
public class PopupController {

    private final PopupService popupService;

    @GetMapping
    public ResponseEntity<List<PopupDTO>> list() {
        return ResponseEntity.ok(popupService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PopupDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(popupService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PopupDTO> create(@Valid @RequestBody PopupUpsertDTO dto) {
        log.debug("REST request to create a popup: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(popupService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PopupDTO> update(@PathVariable Long id, @Valid @RequestBody PopupUpsertDTO dto) {
        log.debug("REST request to update popup ID: {}", id);
        return ResponseEntity.ok(popupService.update(id, dto));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<PopupDTO> activate(@PathVariable Long id) {
        log.debug("REST request to activate popup ID: {}", id);
        return ResponseEntity.ok(popupService.activate(id));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<PopupDTO> deactivate(@PathVariable Long id) {
        log.debug("REST request to deactivate popup ID: {}", id);
        return ResponseEntity.ok(popupService.deactivate(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete popup ID: {}", id);
        popupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
