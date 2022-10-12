package soma.everyonepick.api.pose.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "단체포즈 모델")
public class PoseDto {
    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Schema(description = "인원수")
    @JsonProperty(index = 1)
    private String peopleNum;

    @Schema(description = "단체포즈 URL")
    @JsonProperty(index = 2)
    private String poseUrl;
}
