package athl.logistics.athl_logistics.configuration;

import athl.logistics.athl_logistics.web.errors.RestAccessDeniedHandler;
import athl.logistics.athl_logistics.web.errors.RestAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .cors(Customizer.withDefaults())
                        .authorizeHttpRequests(
                                authorize -> authorize
                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .requestMatchers("/api/v1/authenticate").permitAll()
                                        .requestMatchers("/api/v1/users/register").hasAnyRole( "SUPER_ADMIN")

                                        .requestMatchers("/api/v1/users/*/reset-password").hasAnyRole( "SUPER_ADMIN")
                                        .requestMatchers("/api/v1/users/*/block").hasAnyRole( "SUPER_ADMIN")
                                        .requestMatchers("/api/v1/users/*/unblock").hasAnyRole( "SUPER_ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole( "SUPER_ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasAnyRole( "SUPER_ADMIN")
                                        .requestMatchers("/api/v1/users/activation").permitAll()
                                        .requestMatchers("/api/v1/users/resend-activation-code").permitAll()
                                        .requestMatchers("/api/v1/users/current-user").authenticated()

                                        // Services : liste publique (filtrée par ServiceOfferingServiceImpl selon
                                        // l'authentification), détail par slug public (publiés uniquement),
                                        // tout le reste (détail par id, création, modification, publier/
                                        // dépublier, suppression) réservé aux admins.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/services").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/api/v1/services/slug/**").permitAll()
                                        .requestMatchers("/api/v1/services/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Carrières : même principe que Services (liste publique filtrée,
                                        // reste réservé aux admins), sans détail par slug ici.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/jobs").permitAll()
                                        .requestMatchers("/api/v1/jobs/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Domaines de carrière : liste ouverte, publique en lecture (filtres du
                                        // front), création/suppression réservées aux admins.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/job-domains").permitAll()
                                        .requestMatchers("/api/v1/job-domains/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Équipe : liste publique (page Équipe du site), pas de notion de
                                        // brouillon/publié ici — gestion réservée aux admins.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/team").permitAll()
                                        .requestMatchers("/api/v1/team/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Réalisations/Projets : même principe que Services (liste publique
                                        // filtrée selon l'authentification), sans détail par slug.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/projects").permitAll()
                                        .requestMatchers("/api/v1/projects/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Paramètres du site (compteurs + coordonnées) : lecture publique
                                        // (accueil, footer), modification réservée aux admins.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/home-stats").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/home-stats").hasAnyRole("SUPER_ADMIN", "ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/api/v1/site-settings/contact").permitAll()
                                        .requestMatchers(HttpMethod.PUT, "/api/v1/site-settings/contact").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Popups : liste publique filtrée sur les actives (une par page),
                                        // gestion complète (CRUD + activation) réservée aux admins.
                                        .requestMatchers(HttpMethod.GET, "/api/v1/popups").permitAll()
                                        .requestMatchers("/api/v1/popups/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        // Demandes de devis / candidatures : soumission publique (formulaires du
                                        // site vitrine), consultation et suivi (liste, statut, suppression)
                                        // réservés aux admins — ce sont des soumissions de visiteurs, pas un
                                        // contenu que le BO crée.
                                        .requestMatchers(HttpMethod.POST, "/api/v1/quotes").permitAll()
                                        .requestMatchers("/api/v1/quotes/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/api/v1/applications").permitAll()
                                        .requestMatchers("/api/v1/applications/**").hasAnyRole("SUPER_ADMIN", "ADMIN")

                                        .requestMatchers("/ws/**").permitAll() // connexions WebSocket
                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                        .anyRequest().authenticated()
                        )
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .exceptionHandling(handling -> handling
                                .authenticationEntryPoint(restAuthenticationEntryPoint)
                                .accessDeniedHandler(restAccessDeniedHandler)
                        )
                        .oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                                .authenticationEntryPoint(restAuthenticationEntryPoint)
                        )
                        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Définir BCrypt comme algorithme par défaut
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());
        encoders.put(null, new BCryptPasswordEncoder());
        encoders.put("pbkdf2", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());
        encoders.put("scrypt", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}
