package athl.logistics.athl_logistics.web.resource.authenticate;

import athl.logistics.athl_logistics.service.UserService;
import athl.logistics.athl_logistics.service.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current-user")
    public ResponseEntity<CurrentUserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterUserDTO dto) {
        log.debug("REST request to register user: {}", dto);
        UserDTO savedUser = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/activation")
    public ResponseEntity<String> activateUser(@Valid @RequestBody ActivateAccountDTO activationData) {
        log.debug("REST Request to activate User with code: {}", activationData.getCode());
        userService.activateUser(activationData);
        return ResponseEntity.ok("User activated successfully.");
    }

    @PutMapping("/update-profile")
    public ResponseEntity<CurrentUserDTO> updateProfile(@Valid @RequestBody UpdateProfileDTO dto) {
        log.debug("REST request to update current user's profile: {}", dto);
        return ResponseEntity.ok(userService.updateCurrentUser(dto));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO dto) {
        log.debug("REST request to change current user's password");
        userService.changeCurrentUserPassword(dto);
        return ResponseEntity.ok("Password changed successfully.");
    }

    @PostMapping("/resend-activation-code")
    public ResponseEntity<String> resendActivationCode(@RequestBody Map<String, Long> payload) {
        Long userId = payload.get("userId");
        log.debug("REST Request to resend activation code for User ID: {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("Le champ 'userId' est requis.");
        }
        userService.resendActivationCode(userId);
        return ResponseEntity.ok("New activation code sent successfully.");
    }

    @PostMapping("/{userId}/reset-password")
    public ResponseEntity<String> resetUserPassword(@PathVariable Long userId) {
        log.debug("REST request from an admin to reset password for user ID: {}", userId);
        userService.resetUserPassword(userId);
        return ResponseEntity.ok("New password generated and sent by email.");
    }

}
