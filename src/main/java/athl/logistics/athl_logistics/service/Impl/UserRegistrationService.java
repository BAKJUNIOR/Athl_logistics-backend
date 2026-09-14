package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.models.UserRole;
import athl.logistics.athl_logistics.repositories.UserRepository;
import athl.logistics.athl_logistics.repositories.UserRoleRepository;
import athl.logistics.athl_logistics.service.dto.RegisterUserDTO;
import athl.logistics.athl_logistics.service.dto.UserDTO;
import athl.logistics.athl_logistics.service.mappers.UserMapper;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserUtilityService userUtilityService;

    @Transactional
    public UserDTO registerUser(RegisterUserDTO dto) {
        log.debug("Request to register user: {}", dto);

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new AccountResourceException("Un compte existe déjà avec cet email : " + dto.getEmail(), HttpStatus.CONFLICT);
        }

        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isBlank()
                && userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new AccountResourceException("Un compte existe déjà avec ce numéro de téléphone : " + dto.getPhoneNumber(), HttpStatus.CONFLICT);
        }

        UserRole role = userRoleRepository.findByRoleName(dto.getRoleName())
                .orElseThrow(() -> new AccountResourceException("Rôle introuvable : " + dto.getRoleName()));

        Set<UserRole> roles = new HashSet<>();
        roles.add(role);

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        // Mot de passe temporaire inutilisable : l'utilisateur en choisit un à lui lors de
        // l'activation de son compte (via le code OTP envoyé par mail, cf. saveActivationCode ci-dessous).
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
        user.setRoles(roles);
        user.setCreationDate(Instant.now());
        user.setActive(false);

        User savedUser = userRepository.save(user);
        UserDTO response = userMapper.fromEntity(savedUser);
        response.setPassword(null);

        userUtilityService.saveActivationCode(response);
        return response;
    }
}
