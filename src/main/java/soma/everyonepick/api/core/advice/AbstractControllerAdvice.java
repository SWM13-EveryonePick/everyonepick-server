package soma.everyonepick.api.core.advice;

import kr.co.knowledgerally.api.core.dto.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 추상 컨트롤러 어드바이스
 */
@Slf4j
public abstract class AbstractControllerAdvice {
    protected ResponseEntity<ApiResult<?>> handleException(Throwable e, HttpStatus status) {
        log.error(e.getMessage(), e);
        return handleException(e, status, status.value());
    }

    protected ResponseEntity<ApiResult<?>> handleException(Throwable e, HttpStatus status, int errorCode) {
        return ResponseEntity.status(status)
                .body(ApiResult.fail(e.getMessage()));
    }
}
