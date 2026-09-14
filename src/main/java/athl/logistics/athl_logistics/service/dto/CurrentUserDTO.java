package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.User;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CurrentUserDTO {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String profilePictureUrl;
    private Instant creationDate;
    private boolean isActive;
    private Set<UserRoleDTO> roles;

    public CurrentUserDTO(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.creationDate = user.getCreationDate();
        this.isActive = user.isActive();
        this.roles = user.getRoles() != null ? user.getRoles().stream()
                .map(UserRoleDTO::new)
                .collect(Collectors.toSet()) : null;
    }
}
