package id.holigo.services.holigohotelservice.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class RetrossErrorException extends RuntimeException {
    public RetrossErrorException() {
        super();
    }

    public RetrossErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrossErrorException(String message) {
        super(message);
    }

    public RetrossErrorException(Throwable cause) {
        super(cause);
    }
}
