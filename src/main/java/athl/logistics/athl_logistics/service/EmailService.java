package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.dto.UserValidationCodeDTO;

public interface EmailService {

    void sendActivationCode(UserValidationCodeDTO userValidationCodeDTO);
    void sendAccountCreationEmail(String userType, UserDTO userDTO, String plainPassword);
    void sendPasswordResetByAdmin(UserDTO userDTO, String newPassword);
    void sendAccountStatusEmail(UserDTO userDTO, String reason, String subject);

}
