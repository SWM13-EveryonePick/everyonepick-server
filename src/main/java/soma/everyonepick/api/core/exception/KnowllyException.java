package soma.everyonepick.api.core.exception;

/**
 * 널리 도메인 비즈니스 예외 (500 Server Error)
 */
public class KnowllyException extends RuntimeException {

    public KnowllyException() {
    }

    public KnowllyException(String message) {
        super(message);
    }

    public KnowllyException(String message, Throwable cause) {
        super(message, cause);
    }

    public KnowllyException(Throwable cause) {
        super(cause);
    }

    public KnowllyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
