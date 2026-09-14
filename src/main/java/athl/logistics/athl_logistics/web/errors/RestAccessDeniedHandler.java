package athl.logistics.athl_logistics.web.errors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Même logique que RestAuthenticationEntryPoint, pour le cas 403 : l'utilisateur est bien
// authentifié mais son rôle ne l'autorise pas pour cette route (ex: /users/register réservé à ADMIN).
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException {
        JsonErrorWriter.write(response, HttpStatus.FORBIDDEN.value(),
                "Accès refusé : votre rôle ne permet pas cette action.");
    }
}
