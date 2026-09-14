package athl.logistics.athl_logistics.service;

import athl.logistics.athl_logistics.service.dto.ActivateAccountDTO;
import athl.logistics.athl_logistics.service.dto.ChangePasswordDTO;
import athl.logistics.athl_logistics.service.dto.CurrentUserDTO;
import athl.logistics.athl_logistics.service.dto.RegisterUserDTO;
import athl.logistics.athl_logistics.service.dto.UpdateProfileDTO;
import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.dto.UserSummaryDTO;
import athl.logistics.athl_logistics.service.dto.UserValidationCodeDTO;

import java.util.List;

public interface UserService {
    void saveActivationCode(UserDTO userDTO);
    UserDTO registerUser(RegisterUserDTO dto);
    void activateUser(ActivateAccountDTO activationDetails);
    void resendActivationCode(String email);
    UserValidationCodeDTO getActivationCodeDetails(String code);
    CurrentUserDTO getCurrentUser();
    CurrentUserDTO updateCurrentUser(UpdateProfileDTO dto);
    void changeCurrentUserPassword(ChangePasswordDTO dto);
    void resetUserPassword(Long userId);
    void deleteByUserId(Long userId);
    List<UserSummaryDTO> listUsers();
    void blockUser(Long userId);
    void unblockUser(Long userId);

}
