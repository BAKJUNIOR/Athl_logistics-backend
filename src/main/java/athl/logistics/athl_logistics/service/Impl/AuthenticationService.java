package athl.logistics.athl_logistics.service.Impl;

import athl.logistics.athl_logistics.models.User;
import athl.logistics.athl_logistics.repositories.UserRepository;
import athl.logistics.athl_logistics.web.errors.AccountResourceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    @Transactional
    public Optional<User> getUserWithAuthorities() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof Jwt)) {
            log.debug("Invalid authentication principal: {}", principal);
            return Optional.empty();
        }

        Jwt jwt = (Jwt) principal;
        String email = jwt.getSubject();
        log.debug("Extracted email from Jwt: '{}'", email);
        if (email == null) {
            log.debug("Email is null in JWT");
            return Optional.empty();
        }

        email = email.trim();
        return userRepository.findByEmail(email)
                .map(user -> {
                    Hibernate.initialize(user.getRoles());
                    return user;
                });
    }

    @Transactional
    public User getCurrentUserEntity() {
        return getUserWithAuthorities()
                .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }
}
