package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사진선택 작업 모델")
public class PickDto {

    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Schema(description = "본인선택 여부")
    @JsonProperty(index = 1)
    private Boolean isDone;

    @Schema(description = "만료시점")
    @JsonProperty(index = 2)
    private LocalDateTime expired_at;

//    @Schema(description = "사진선택 작업에 포함된 사진들")
//    @JsonProperty(index = 3)
//    private List<PhotoDto.PhotoResponseDto> photos;
}
