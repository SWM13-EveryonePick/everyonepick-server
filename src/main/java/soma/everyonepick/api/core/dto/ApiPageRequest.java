package soma.everyonepick.api.core.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 페이징 API 요청 모델
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="페이징 API 요청 모델")
@EqualsAndHashCode
public class ApiPageRequest {
    @Schema(description = "요청 페이지 번호 (0부터 시작)", example = "0")
    private int page = 0;

    @Schema(description = "요청 페이지 크기 (기본 : 10)", example = "10")
    private int size = 10;

    public Pageable convert() {
        return PageRequest.of(page, size);
    }

    public Pageable convertWithNewestSort() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }

}
