package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String profilePictureUrl;
    private Instant creationDate;
    private boolean isActive;
    private Set<UserRoleDTO> roles;

    public UserDTO(User user) {
        log.debug("Constructing UserDTO from User: {}", user);
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.creationDate = user.getCreationDate();
        this.isActive = user.isActive();
        this.roles = user.getRoles() != null ? user.getRoles().stream()
                .map(UserRoleDTO::new)
                .collect(Collectors.toSet()) : null;
        log.debug("Constructed UserDTO: id={}, email={}, isActive={}, roles={}", id, email, isActive, roles);
    }

}
