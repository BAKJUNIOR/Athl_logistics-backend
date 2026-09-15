package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.TeamMemberDTO;
import athl.logistics.athl_logistics.service.dto.TeamMemberUpsertDTO;

import java.util.List;

public interface TeamMemberService {
    List<TeamMemberDTO> list();

    TeamMemberDTO create(TeamMemberUpsertDTO dto);

    TeamMemberDTO update(Long id, TeamMemberUpsertDTO dto);

    void delete(Long id);
}
