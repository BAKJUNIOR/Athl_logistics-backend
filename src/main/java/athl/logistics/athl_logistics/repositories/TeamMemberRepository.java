package athl.logistics.athl_logistics.repositories;

import athl.logistics.athl_logistics.models.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findAllByOrderBySortOrderAsc();
}
