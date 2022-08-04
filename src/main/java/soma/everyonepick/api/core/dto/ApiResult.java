package soma.everyonepick.api.core.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import soma.everyonepick.api.core.message.ResponseMessage;

/**
 * API 응답 공통 모델
 * @param <T> DTO 클래스 타입
 */
@Getter
@Schema(description="API 응답 공통 모델")
public class ApiResult<T> {

    @Schema(description = "결과 메세지")
    protected final String message;

    @Schema(description = "응답 데이터, 실패시 null")
    protected final T data;

    @Schema(description = "요청 서버 시간")
    protected final long timestamp;

    public ApiResult(String message, T data) {
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ApiResult<T> ok(T data) {
        return ok(data, ResponseMessage.OK);
    }

    public static <T> ApiResult<T> ok(T data, String message) {
        return new ApiResult<>(message, data);
    }

    public static ApiResult<?> fail(String message) {
        return new ApiResult<>(message, null);
    }
}
