package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사진 모델")
public class PhotoDto {
    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Schema(description = "사진 S3 URL")
    @JsonProperty(index = 1)
    private String photoUrl;
}
