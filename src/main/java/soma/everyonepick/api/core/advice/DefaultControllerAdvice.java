package soma.everyonepick.api.core.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.core.exception.*;

import javax.annotation.Priority;

/**
 * 기본 컨트롤러 어드바이스, 사실상 그냥 예외 핸들러
 */
@Priority(20)
@RestControllerAdvice
public class DefaultControllerAdvice extends AbstractControllerAdvice {
    @ExceptionHandler(value = { Exception.class, EveryonepickException.class })
    public ResponseEntity<ApiResult<?>> handleUnknownException(Exception e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { BadRequestException.class })
    public ResponseEntity<ApiResult<?>> handleBadRequestException(Exception e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ResourceNotFoundException.class})
    public ResponseEntity<ApiResult<?>> handleNotFoundException(Exception e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { AccessNotAllowedException.class})
    public ResponseEntity<ApiResult<?>> handleForbiddenException(Exception e) {
        return handleException(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { UnauthorizedException.class})
    public ResponseEntity<ApiResult<?>> handleUnauthorizedException(Exception e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

}
