package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

// DTO dédié à la liste des utilisateurs côté admin (BO) : contrairement à UserDTO,
// l'id est exposé volontairement car l'admin en a besoin pour bloquer/débloquer/
// réinitialiser le mot de passe/supprimer un utilisateur. Le mot de passe n'y figure jamais.
@Data
@NoArgsConstructor
public class UserSummaryDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profilePictureUrl;
    private Instant creationDate;
    private boolean isActive;
    private Set<UserRoleDTO> roles;

    public UserSummaryDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.creationDate = user.getCreationDate();
        this.isActive = user.isActive();
        this.roles = user.getRoles() != null ? user.getRoles().stream()
                .map(UserRoleDTO::new)
                .collect(Collectors.toSet()) : null;
    }
}
