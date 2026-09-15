package athl.logistics.athl_logistics.configuration;

import athl.logistics.athl_logistics.models.HomeStat;
import athl.logistics.athl_logistics.models.JobDomain;
import athl.logistics.athl_logistics.models.SiteContact;
import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.models.UserRole;
import athl.logistics.athl_logistics.models.enums.HomeStatKey;
import athl.logistics.athl_logistics.models.enums.RoleName;
import athl.logistics.athl_logistics.repositories.HomeStatRepository;
import athl.logistics.athl_logistics.repositories.JobDomainRepository;
import athl.logistics.athl_logistics.repositories.SiteContactRepository;
import athl.logistics.athl_logistics.repositories.UserRepository;
import athl.logistics.athl_logistics.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private static final String DEFAULT_ADMIN_EMAIL = "beugreadroh@gmail.com";
    private static final String DEFAULT_ADMIN_PASSWORD = "Admin123@";

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final JobDomainRepository jobDomainRepository;
    private final HomeStatRepository homeStatRepository;
    private final SiteContactRepository siteContactRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedRoles();
        seedAdmin();
        seedJobDomains();
        seedHomeStats();
        seedSiteContact();
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

        UserRole adminRole = userRoleRepository.findByRoleName(RoleName.SUPER_ADMIN)
                .orElseThrow(() -> new IllegalStateException("Le rôle ADMIN doit exister avant de créer l'admin initial"));

        Set<UserRole> roles = new HashSet<>();
        roles.add(adminRole);

        User admin = new User();
        admin.setFirstName("Beugre");
        admin.setLastName("Alain");
        admin.setProfilePictureUrl("https://res.cloudinary.com/drfq0bt4z/image/upload/v1789399962/user-profiles/lrwpczeueh6cjytwlga1.jpg");
        admin.setPhoneNumber("+2250777062160");
        admin.setEmail(DEFAULT_ADMIN_EMAIL);
        admin.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
        admin.setRoles(roles);
        admin.setCreationDate(Instant.now());
        admin.setActive(true);

        userRepository.save(admin);
        log.warn("Compte administrateur initial créé : {} — pensez à changer son mot de passe.", DEFAULT_ADMIN_EMAIL);
    }

    // Point de départ seulement : n'importe quel admin peut ensuite ajouter/supprimer des
    // domaines depuis le formulaire d'offre (voir JobDomainService) — liste ouverte, pas un enum figé.
    private void seedJobDomains() {
        Map<String, String> defaults = new LinkedHashMap<>();
        defaults.put("Chantier", "Construction site");
        defaults.put("Second œuvre", "Finishing works");
        defaults.put("Mobilité", "Mobility");
        defaults.put("Logistique", "Logistics");
        defaults.put("Support", "Support");

        defaults.forEach((labelFr, labelEn) -> {
            if (jobDomainRepository.findByLabelFrIgnoreCase(labelFr).isEmpty()) {
                JobDomain domain = new JobDomain();
                domain.setLabelFr(labelFr);
                domain.setLabelEn(labelEn);
                jobDomainRepository.save(domain);
                log.info("Domaine de carrière créé : {}", labelFr);
            }
        });
    }

    private void seedHomeStats() {
        if (homeStatRepository.count() > 0) return;

        homeStatRepository.save(newHomeStat(HomeStatKey.SITES_DELIVERED, "Chantiers livrés", 2193, 0, "+"));
        homeStatRepository.save(newHomeStat(HomeStatKey.PROJECT_VALUE, "Valeur des projets", 3.16, 2, " M€"));
        homeStatRepository.save(newHomeStat(HomeStatKey.ASSET_VALUE, "Valeur des actifs", 121.2, 1, " M€"));
        log.info("Compteurs de l'accueil initialisés");
    }

    private HomeStat newHomeStat(HomeStatKey key, String label, double value, int decimals, String suffix) {
        HomeStat stat = new HomeStat();
        stat.setKey(key);
        stat.setLabel(label);
        stat.setValue(value);
        stat.setDecimals(decimals);
        stat.setSuffix(suffix);
        return stat;
    }

    private void seedSiteContact() {
        if (siteContactRepository.existsById(1L)) return;

        SiteContact contact = new SiteContact();
        contact.setPhone1("+225 07 78 09 58 58");
        contact.setPhone2("+225 07 09 99 33 47");
        contact.setPhone3("+225 07 58 60 16 27");
        contact.setAddress("Abidjan, Côte d'Ivoire");
        siteContactRepository.save(contact);
        log.info("Coordonnées du site initialisées");
    }
}
