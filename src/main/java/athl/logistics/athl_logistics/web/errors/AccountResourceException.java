package athl.logistics.athl_logistics.web.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AccountResourceException extends RuntimeException {

    private final HttpStatus status;

    public AccountResourceException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public AccountResourceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
