package soma.everyonepick.api.album.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사진선택 정보 유저모델")
public class PickInfoUserDto {
    @Schema(description = "타임아웃 시간(분)")
    @JsonProperty(index = 0)
    private Long timeOut;

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "사진선택 정보 유저 응답모델")
    public static class PickInfoUserResponseDto extends PickInfoUserDto {
        @Schema(description = "전체 멤버 수")
        @JsonProperty(index = 1)
        private Long userCnt;

        @Schema(description = "완료한 멤버 수")
        @JsonProperty(index = 2)
        private Long pickUserCnt;
    }
}
