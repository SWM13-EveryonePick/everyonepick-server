package soma.everyonepick.api.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "얼굴정보 요청 모델", hidden = true)
public class FaceInfoRequestDto {
    @Schema(description = "사용자 아이디", hidden = true)
    @JsonProperty(index = 0)
    private Long userId;

    @Schema(description = "얼굴 이미지 base64 인코딩 문자열", hidden = true)
    @JsonProperty(index = 1)
    private String image;
}
