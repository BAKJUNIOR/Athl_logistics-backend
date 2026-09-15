package athl.logistics.athl_logistics.web.resource.team;

import athl.logistics.athl_logistics.service.TeamMemberService;
import athl.logistics.athl_logistics.service.dto.TeamMemberDTO;
import athl.logistics.athl_logistics.service.dto.TeamMemberUpsertDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Public en lecture (page Équipe du site), gestion réservée aux admins.
@Slf4j
@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamMemberController {

    private final TeamMemberService teamMemberService;

    @GetMapping
    public ResponseEntity<List<TeamMemberDTO>> list() {
        return ResponseEntity.ok(teamMemberService.list());
    }

    @PostMapping
    public ResponseEntity<TeamMemberDTO> create(@Valid @RequestBody TeamMemberUpsertDTO dto) {
        log.debug("REST request to create a team member: {}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(teamMemberService.create(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamMemberDTO> update(@PathVariable Long id, @Valid @RequestBody TeamMemberUpsertDTO dto) {
        log.debug("REST request to update team member ID: {}", id);
        return ResponseEntity.ok(teamMemberService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete team member ID: {}", id);
        teamMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
