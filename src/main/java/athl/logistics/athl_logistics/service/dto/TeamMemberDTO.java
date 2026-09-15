package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.TeamMember;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class TeamMemberDTO {
    private Long id;
    private String name;
    private String roleFr;
    private String roleEn;
    private String photo;
    private int sortOrder;
    private Instant updatedAt;

    public TeamMemberDTO(TeamMember entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.roleFr = entity.getRoleFr();
        this.roleEn = entity.getRoleEn();
        this.photo = entity.getPhoto();
        this.sortOrder = entity.getSortOrder();
        this.updatedAt = entity.getUpdatedAt();
    }
}
