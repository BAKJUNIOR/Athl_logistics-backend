package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.service.UserService;
import athl.logistics.athl_logistics.service.dto.ActivateAccountDTO;
import athl.logistics.athl_logistics.service.dto.ChangePasswordDTO;
import athl.logistics.athl_logistics.service.dto.CurrentUserDTO;
import athl.logistics.athl_logistics.service.dto.RegisterUserDTO;
import athl.logistics.athl_logistics.service.dto.UpdateProfileDTO;
import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.dto.UserSummaryDTO;
import athl.logistics.athl_logistics.service.dto.UserValidationCodeDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AuthenticationService authenticationService;
    private final UserUtilityService userUtilityService;
    private final UserRegistrationService userRegistrationService;

    @Override
    public void saveActivationCode(UserDTO userDTO) {
        userUtilityService.saveActivationCode(userDTO);
    }

    @Override
    @Transactional
    public UserDTO registerUser(RegisterUserDTO dto) {
        return userRegistrationService.registerUser(dto);
    }

    @Override
    public void activateUser(ActivateAccountDTO activation) {
        userUtilityService.activateUser(activation);
    }

    @Override
    public void resendActivationCode(String email) {
        userUtilityService.resendActivationCode(email);
    }

    @Override
    public UserValidationCodeDTO getActivationCodeDetails(String code) {
        return userUtilityService.getActivationCodeDetails(code);
    }

    @Override
    @Transactional
    public CurrentUserDTO getCurrentUser() {
        User user = authenticationService.getCurrentUserEntity();
        log.debug("Fetching current user with email: {}", user.getEmail());
        return new CurrentUserDTO(user);
    }

    @Override
    @Transactional
    public CurrentUserDTO updateCurrentUser(UpdateProfileDTO dto) {
        User user = authenticationService.getCurrentUserEntity();
        userUtilityService.updateProfile(user, dto);
        return new CurrentUserDTO(user);
    }

    @Override
    @Transactional
    public void changeCurrentUserPassword(ChangePasswordDTO dto) {
        User user = authenticationService.getCurrentUserEntity();
        userUtilityService.changePassword(user, dto);
    }

    @Override
    @Transactional
    public void resetUserPassword(Long userId) {
        log.debug("Admin request to reset password for user ID: {}", userId);
        userUtilityService.resetUserPassword(userId);
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        log.debug("Request to delete user with ID: {}", userId);
        userUtilityService.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public List<UserSummaryDTO> listUsers() {
        return userUtilityService.listUsers();
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        log.debug("Admin request to block user ID: {}", userId);
        userUtilityService.blockUser(userId);
    }

    @Override
    @Transactional
    public void unblockUser(Long userId) {
        log.debug("Admin request to unblock user ID: {}", userId);
        userUtilityService.unblockUser(userId);
    }
}
