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
@Schema(description = "각 유저의 사진선택 정보 모델")
public class PickInfoPhotoDto {
    @Schema(description = "사진선택을 한 userId")
    @JsonProperty(index = 0)
    private Long userId;

    @Schema(description = "유저가 선택한 photoId 리스트")
    @JsonProperty(index = 1)
    private List<Long> photoIds;
}
