package athl.logistics.athl_logistics.web.resource.authenticate;

import athl.logistics.athl_logistics.service.UserService;
import athl.logistics.athl_logistics.service.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.ok("Compte activé avec succès.");
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
        return ResponseEntity.ok("Mot de passe modifié avec succès.");
    }

    @PostMapping("/resend-activation-code")
    public ResponseEntity<String> resendActivationCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        log.debug("REST Request to resend activation code for email: {}", email);
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Le champ 'email' est requis.");
        }
        userService.resendActivationCode(email);
        return ResponseEntity.ok("Nouveau code d'activation envoyé avec succès.");
    }

    @PostMapping("/{userId}/reset-password")
    public ResponseEntity<String> resetUserPassword(@PathVariable Long userId) {
        log.debug("REST request from an admin to reset password for user ID: {}", userId);
        userService.resetUserPassword(userId);
        return ResponseEntity.ok("Nouveau mot de passe généré et envoyé par e-mail.");
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> listUsers() {
        log.debug("REST request from an admin to list all users");
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/{userId}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long userId) {
        log.debug("REST request from an admin to block user ID: {}", userId);
        userService.blockUser(userId);
        return ResponseEntity.ok("Utilisateur bloqué avec succès.");
    }

    @PostMapping("/{userId}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Long userId) {
        log.debug("REST request from an admin to unblock user ID: {}", userId);
        userService.unblockUser(userId);
        return ResponseEntity.ok("Utilisateur débloqué avec succès.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        log.debug("REST request from an admin to delete user ID: {}", userId);
        userService.deleteByUserId(userId);
        return ResponseEntity.ok("Utilisateur supprimé avec succès.");
    }

}
