package soma.everyonepick.api.album.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "합성완료사진 요청 모델")
public class ResultPhotoRequestDto {
    @Schema(description = "삭제할 합성완료사진")
    @JsonProperty(index = 0)
    private List<ResultPhotoDto> resultPhotos;
}
