package soma.everyonepick.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 모델")
public class UserDto {

    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Schema(description = "닉네임")
    @JsonProperty(index = 1)
    private String nickname;

    @Schema(description = "카카오 회원 아이디")
    @JsonProperty(index = 2)
    private String clientId;

    @Schema(description = "카카오 썸네일 이미지")
    @JsonProperty(index = 3)
    private String thumbnailImageUrl;

    @Schema(description = "푸쉬알림 동의 여부")
    @JsonProperty(index = 4)
    private Boolean isPushActive;
}


