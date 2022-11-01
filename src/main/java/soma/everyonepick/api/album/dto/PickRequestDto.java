package soma.everyonepick.api.album.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사진선택 작업 요청모델")
public class PickRequestDto {
    @Schema(description = "사진선택 작업에 포함될 사진들")
    @JsonProperty(index = 0)
    private List<PhotoDto> photos;
}
