package soma.everyonepick.api.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 얼굴 정보 모델")
public class FaceInfoDto {
    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Schema(description = "얼굴 임베딩 정보")
    @JsonProperty(index = 1)
    private String faceFeature;
}
