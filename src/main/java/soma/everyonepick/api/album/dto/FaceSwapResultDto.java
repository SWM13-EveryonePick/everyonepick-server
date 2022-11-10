package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "합성결과 모델")
public class FaceSwapResultDto {

    @Schema(description = "사진선택 작업 id", hidden = true)
    @JsonProperty(index = 0)
    private Long pick_id;

    @Schema(description = "합성결과 이미지 base64 인코딩", hidden = true)
    @JsonProperty(index = 1)
    private String face_swap_result;
}
