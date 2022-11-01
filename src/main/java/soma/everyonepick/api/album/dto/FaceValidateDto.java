package soma.everyonepick.api.album.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "얼굴 예외처리 요청모델", hidden = true)
public class FaceValidateDto {
    @Schema(description = "멤버 수")
    @JsonProperty(index = 0)
    private Long userCnt;

    @Schema(description = "사진선택 작업에 포함될 사진들", hidden = true)
    @JsonProperty(index = 1)
    private List<PhotoDto.PhotoResponseDto> photos;
}
