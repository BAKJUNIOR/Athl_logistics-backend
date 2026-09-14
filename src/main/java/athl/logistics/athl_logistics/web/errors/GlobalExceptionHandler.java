package athl.logistics.athl_logistics.web.errors;

import athl.logistics.athl_logistics.response.ResponseUtil;
import athl.logistics.athl_logistics.response.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountResourceException.class)
    public ResponseEntity<ResponseWrapper<String>> handleAccountResourceException(AccountResourceException ex) {
        log.debug("Business error: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(ResponseUtil.error(ex.getStatus().value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(" ; "));
        log.debug("Validation error: {}", message);
        return ResponseEntity.badRequest().body(ResponseUtil.error(HttpStatus.BAD_REQUEST.value(), message));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseWrapper<String>> handleAuthenticationException(AuthenticationException ex) {
        log.debug("Authentication failed: {}", ex.getMessage());
        String message = (ex instanceof DisabledException)
                ? "Ce compte n'est pas encore activé. Utilisez le code d'activation reçu par email pour définir votre mot de passe."
                : "Email ou mot de passe incorrect.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseUtil.error(HttpStatus.UNAUTHORIZED.value(), message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.debug("Invalid argument: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ResponseUtil.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<String>> handleUnexpectedException(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.internalServerError()
                .body(ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Une erreur inattendue est survenue."));
    }
}
