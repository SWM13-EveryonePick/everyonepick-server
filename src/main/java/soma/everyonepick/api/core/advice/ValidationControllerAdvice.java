package soma.everyonepick.api.core.advice;

import kr.co.knowledgerally.api.core.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Priority;

/**
 * 유효 검증 예외 처리 어드바이스
 */
@Priority(5)
@RestControllerAdvice
public class ValidationControllerAdvice extends AbstractControllerAdvice {
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public ResponseEntity<ApiResult<?>> handleValidationException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        String message = String.format("[%s : %s]",
                fieldError.getField(), fieldError.getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail(message));
    }
}
