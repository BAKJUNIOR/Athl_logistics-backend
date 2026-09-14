package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.models.UserValidationCode;
import athl.logistics.athl_logistics.repositories.UserRepository;
import athl.logistics.athl_logistics.repositories.UserValidationCodeRepository;
import athl.logistics.athl_logistics.service.EmailService;
import athl.logistics.athl_logistics.service.dto.ActivateAccountDTO;
import athl.logistics.athl_logistics.service.dto.ChangePasswordDTO;
import athl.logistics.athl_logistics.service.dto.UpdateProfileDTO;
import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.dto.UserValidationCodeDTO;
import athl.logistics.athl_logistics.service.mappers.UserMapper;
import athl.logistics.athl_logistics.service.mappers.UserValidationCodeMapper;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserUtilityService {

    private final UserRepository userRepository;
    private final UserValidationCodeRepository userValidationCodeRepository;
    private final UserMapper userMapper;
    private final UserValidationCodeMapper validationCodeMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private String generateNewCode() {
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        return String.format("%06d", randomInteger);
    }

    // Le code est déjà persisté à ce stade : un souci SMTP ponctuel (identifiants mail
    // invalides, serveur indisponible...) ne doit ni faire échouer la création du compte, ni
    // annuler la transaction. L'utilisateur pourra toujours obtenir son code via
    // resend-activation-code une fois l'envoi de mail fonctionnel.
    private void sendActivationCodeBestEffort(UserValidationCodeDTO validationCodeDTO) {
        try {
            emailService.sendActivationCode(validationCodeDTO);
        } catch (RuntimeException e) {
            log.error("Échec d'envoi du code d'activation à {} : {}",
                    validationCodeDTO.getUser().getEmail(), e.getMessage());
        }
    }

    @Transactional
    public void saveActivationCode(UserDTO userDTO) {
        log.debug("Saving activation code for user ID: {}", userDTO.getId());

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new AccountResourceException("Utilisateur introuvable", HttpStatus.NOT_FOUND));

        UserValidationCodeDTO validationCodeDTO = new UserValidationCodeDTO();
        validationCodeDTO.setUser(userMapper.fromEntity(user));
        Instant creation = Instant.now();
        validationCodeDTO.setCreation(creation);
        validationCodeDTO.setExpiration(creation.plus(10, MINUTES));
        String code = generateNewCode();
        validationCodeDTO.setCode(code);

        UserValidationCode validationCodeEntity = validationCodeMapper.toEntity(validationCodeDTO);
        userValidationCodeRepository.save(validationCodeEntity);
        sendActivationCodeBestEffort(validationCodeDTO);

        log.debug("Activation code saved: {}", code);
    }

    @Transactional
    public void activateUser(ActivateAccountDTO activation) {
        String code = activation.getCode();
        log.debug("Activating user with code: {}", code);

        UserValidationCodeDTO validationCodeDTO = getActivationCodeDetails(code);
        if (Instant.now().isAfter(validationCodeDTO.getExpiration())) {
            throw new AccountResourceException("Ce code d'activation a expiré. Demandez-en un nouveau.");
        }

        User userToActivate = userRepository.findById(validationCodeDTO.getUser().getId())
                .orElseThrow(() -> new AccountResourceException("Utilisateur introuvable", HttpStatus.NOT_FOUND));

        // L'utilisateur choisit son propre mot de passe lors de cette première connexion.
        userToActivate.setPassword(passwordEncoder.encode(activation.getNewPassword()));
        userToActivate.setActive(true);
        userRepository.save(userToActivate);

        // Le code est à usage unique : on l'invalide une fois le compte activé.
        userValidationCodeRepository.deleteByUserId(userToActivate.getId());

        log.debug("User activated successfully: {}", userToActivate);
    }

    @Transactional
    public void updateProfile(User user, UpdateProfileDTO dto) {
        log.debug("Updating profile for user ID: {}", user.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());
        userRepository.save(user);
    }

    @Transactional
    public void changePassword(User user, ChangePasswordDTO dto) {
        log.debug("Changing password for user ID: {}", user.getId());
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new AccountResourceException("Le mot de passe actuel est incorrect.");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    // Pas de flux "mot de passe oublié" self-service dans ce BO : un utilisateur bloqué doit
    // passer par un ADMIN, qui lui génère un nouveau mot de passe temporaire envoyé par email.
    @Transactional
    public void resetUserPassword(Long userId) {
        log.debug("Admin resetting password for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Utilisateur introuvable", HttpStatus.NOT_FOUND));

        String newPassword = UUID.randomUUID().toString().substring(0, 12);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        try {
            emailService.sendPasswordResetByAdmin(userMapper.fromEntity(user), newPassword);
        } catch (RuntimeException e) {
            log.error("Échec d'envoi du nouveau mot de passe à {} : {}", user.getEmail(), e.getMessage());
        }
    }

    @Transactional
    public void resendActivationCode(Long userId) {
        log.debug("Resending activation code for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Utilisateur introuvable", HttpStatus.NOT_FOUND));

        Optional<UserValidationCode> existingCodeOpt = userValidationCodeRepository.findByUserId(user.getId());

        if (existingCodeOpt.isPresent()) {
            UserValidationCode existingCode = existingCodeOpt.get();
            if (Instant.now().isBefore(existingCode.getExpiration())) {
                throw new AccountResourceException("Un code d'activation est déjà actif. Merci de patienter avant d'en redemander un.");
            }

            existingCode.setCode(generateNewCode());
            existingCode.setCreation(Instant.now());
            existingCode.setExpiration(Instant.now().plus(10, MINUTES));
            userValidationCodeRepository.save(existingCode);

            UserValidationCodeDTO validationCodeDTO = validationCodeMapper.fromEntity(existingCode);
            sendActivationCodeBestEffort(validationCodeDTO);
        } else {
            saveActivationCode(userMapper.fromEntity(user));
        }

        log.debug("Activation code resent successfully");
    }

    @Transactional
    public UserValidationCodeDTO getActivationCodeDetails(String code) {
        log.debug("Fetching activation code details for code: {}", code);

        UserValidationCode validationCode = userValidationCodeRepository.findByCode(code)
                .orElseThrow(() -> new AccountResourceException("Code d'activation invalide."));

        return validationCodeMapper.fromEntity(validationCode);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        log.debug("Request to delete user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AccountResourceException("Utilisateur introuvable avec l'ID : " + userId, HttpStatus.NOT_FOUND));
        userValidationCodeRepository.deleteByUserId(userId);
        userRepository.delete(user);
    }
}
