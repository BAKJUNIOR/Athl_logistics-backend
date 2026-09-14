package athl.logistics.athl_logistics.configuration;

import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.models.UserRole;
import athl.logistics.athl_logistics.models.enums.RoleName;
import athl.logistics.athl_logistics.repositories.UserRepository;
import athl.logistics.athl_logistics.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private static final String DEFAULT_ADMIN_EMAIL = "admin@athl-logistics.ci";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin@2026!";

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedRoles();
        seedAdmin();
    }

    private void seedRoles() {

        for (RoleName roleName : RoleName.values()) {
            if (userRoleRepository.findByRoleName(roleName).isEmpty()) {
                UserRole role = new UserRole();
                role.setRoleName(roleName);
                userRoleRepository.save(role);
                log.info("Rôle créé : {}", roleName);
            }
        }
    }

    private void seedAdmin() {
        if (userRepository.existsByEmail(DEFAULT_ADMIN_EMAIL)) {
            return;
        }

        UserRole adminRole = userRoleRepository.findByRoleName(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Le rôle ADMIN doit exister avant de créer l'admin initial"));

        Set<UserRole> roles = new HashSet<>();
        roles.add(adminRole);

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("ATHL");
        admin.setEmail(DEFAULT_ADMIN_EMAIL);
        admin.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
        admin.setRoles(roles);
        admin.setCreationDate(Instant.now());
        admin.setActive(true);

        userRepository.save(admin);
        log.warn("Compte administrateur initial créé : {} — pensez à changer son mot de passe.", DEFAULT_ADMIN_EMAIL);
    }
}
