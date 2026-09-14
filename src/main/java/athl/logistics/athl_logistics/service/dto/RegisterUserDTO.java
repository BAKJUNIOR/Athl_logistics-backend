package athl.logistics.athl_logistics.service.dto;

import athl.logistics.athl_logistics.models.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RegisterUserDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    private String phoneNumber;

    @NotNull(message = "Role is required")
    private RoleName roleName;
}
