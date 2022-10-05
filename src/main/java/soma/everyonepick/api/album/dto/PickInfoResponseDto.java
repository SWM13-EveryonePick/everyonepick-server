package soma.everyonepick.api.album.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사진선택 정보 모델")
public class PickInfoResponseDto {
    @Schema(description = "전체 멤버 수")
    @JsonProperty(index = 0)
    private Long userCnt;

    @Schema(description = "완료한 멤버 수")
    @JsonProperty(index = 1)
    private Long pickUserCnt;

    @Schema(description = "남은 시간")
    @JsonProperty(index = 2)
    private Long timeOut;
}
