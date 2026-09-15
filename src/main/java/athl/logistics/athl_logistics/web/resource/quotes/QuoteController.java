package athl.logistics.athl_logistics.web.resource.quotes;

import athl.logistics.athl_logistics.service.QuoteRequestService;
import athl.logistics.athl_logistics.service.dto.QuoteRequestCreateDTO;
import athl.logistics.athl_logistics.service.dto.QuoteRequestDTO;
import athl.logistics.athl_logistics.service.dto.StatusUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Création publique (formulaire "Demander un devis" du site vitrine), consultation/suivi réservés aux admins.
@Slf4j
@RestController
@RequestMapping("/api/v1/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteRequestService quoteRequestService;

    @GetMapping
    public ResponseEntity<List<QuoteRequestDTO>> list() {
        return ResponseEntity.ok(quoteRequestService.list());
    }

    @PostMapping
    public ResponseEntity<QuoteRequestDTO> create(@Valid @RequestBody QuoteRequestCreateDTO dto) {
        log.debug("REST request to create a quote request: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(quoteRequestService.create(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QuoteRequestDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDTO dto) {
        log.debug("REST request to update quote request status ID: {}", id);
        return ResponseEntity.ok(quoteRequestService.updateStatus(id, dto.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete quote request ID: {}", id);
        quoteRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
