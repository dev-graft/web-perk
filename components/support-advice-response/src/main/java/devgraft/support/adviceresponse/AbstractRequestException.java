package devgraft.support.adviceresponse;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AbstractRequestException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public AbstractRequestException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.printStackTrace();
    }

    public AbstractRequestException(final String message) {
        super(message);
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.printStackTrace();
    }
}