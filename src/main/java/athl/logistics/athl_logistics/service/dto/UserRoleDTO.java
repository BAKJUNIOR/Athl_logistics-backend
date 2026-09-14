package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.UserRole;
import athl.logistics.athl_logistics.models.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private RoleName roleName;

    public UserRoleDTO(UserRole userRole) {
        log.debug("Constructing UserRoleDTO from UserRole: {}", userRole);
        this.roleName = userRole.getRoleName();
        log.debug("Constructed UserRoleDTO: roleName={}", roleName);
    }

}
