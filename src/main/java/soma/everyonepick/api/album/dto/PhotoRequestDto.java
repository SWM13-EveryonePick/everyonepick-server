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
@Schema(description = "사진 요청모델")
public class PhotoRequestDto {
    @Schema(description = "삭제할 사진")
    @JsonProperty(index = 0)
    private List<PhotoDto> photos;
}
