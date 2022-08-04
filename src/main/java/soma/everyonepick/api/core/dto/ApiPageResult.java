package soma.everyonepick.api.core.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;
import soma.everyonepick.api.core.message.ResponseMessage;

import java.util.List;

/**
 * 페이징 API 응답 모델
 * @param <T> DTO 클래스 타입
 */
@Getter
@Schema(description="페이징 API 응답 모델")
public class ApiPageResult<T> extends ApiResult<List<T>> {

    @Schema(description = "페이지 번호")
    private final int currentPage;

    @Schema(description = "총합 페이지")
    private final int totalPage;

    @Schema(description = "페이지 크기")
    private final int pageSize;

    @Schema(description = "전체 개수")
    private final long totalElements;

    public ApiPageResult(Page<T> data, String message) {
        super(message, data.getContent());
        this.currentPage = data.getPageable().getPageNumber();
        this.pageSize = data.getPageable().getPageSize();
        this.totalPage = data.getTotalPages();
        this.totalElements = data.getTotalElements();
    }

    public static <T> ApiPageResult<T> ok(Page<T> data) {
        return ok(data, ResponseMessage.OK);
    }

    public static <T> ApiPageResult<T> ok(Page<T> data, String message) {
        return new ApiPageResult<>(data, message);
    }
}
