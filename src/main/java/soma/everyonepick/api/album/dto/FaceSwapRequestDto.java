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
@Schema(description = "합성요청 모델")
public class FaceSwapRequestDto {
    @Schema(description = "사진선택 작업 정보")
    @JsonProperty(index = 0)
    private PickDto.PickDetailDto pick;

    @Schema(description = "각긱 유저들의 사진선택 정보 리스트")
    @JsonProperty(index = 1)
    private List<PickInfoPhotoDto> pickInfoPhotos;
}
