package soma.everyonepick.api.core.exception;

/**
 * 널리 도메인 비즈니스 예외 (500 Server Error)
 */
public class EveryonepickException extends RuntimeException {

    public EveryonepickException() {
    }

    public EveryonepickException(String message) {
        super(message);
    }

    public EveryonepickException(String message, Throwable cause) {
        super(message, cause);
    }

    public EveryonepickException(Throwable cause) {
        super(cause);
    }

    public EveryonepickException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
