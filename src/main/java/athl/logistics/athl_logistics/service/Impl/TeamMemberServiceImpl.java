package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.TeamMember;
import athl.logistics.athl_logistics.repositories.TeamMemberRepository;
import athl.logistics.athl_logistics.service.TeamMemberService;
import athl.logistics.athl_logistics.service.dto.TeamMemberDTO;
import athl.logistics.athl_logistics.service.dto.TeamMemberUpsertDTO;
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
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<TeamMemberDTO> list() {
        return repository.findAllByOrderBySortOrderAsc().stream()
                .map(TeamMemberDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TeamMemberDTO create(TeamMemberUpsertDTO dto) {
        TeamMember entity = new TeamMember();
        applyFields(entity, dto);
        TeamMember saved = repository.save(entity);
        log.info("Membre d'équipe créé : id={}", saved.getId());
        return new TeamMemberDTO(saved);
    }

    @Override
    @Transactional
    public TeamMemberDTO update(Long id, TeamMemberUpsertDTO dto) {
        TeamMember entity = findEntityOrThrow(id);
        applyFields(entity, dto);
        TeamMember saved = repository.save(entity);
        log.info("Membre d'équipe mis à jour : id={}", saved.getId());
        return new TeamMemberDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        TeamMember entity = findEntityOrThrow(id);
        repository.delete(entity);
        log.info("Membre d'équipe supprimé : id={}", id);
    }

    private TeamMember findEntityOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountResourceException("Membre introuvable avec l'ID : " + id, HttpStatus.NOT_FOUND));
    }

    private void applyFields(TeamMember entity, TeamMemberUpsertDTO dto) {
        entity.setName(dto.getName());
        entity.setRoleFr(dto.getRoleFr());
        entity.setRoleEn(dto.getRoleEn());
        entity.setPhoto(dto.getPhoto());
        entity.setSortOrder(dto.getSortOrder());
    }
}
