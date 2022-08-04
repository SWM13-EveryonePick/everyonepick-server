package soma.everyonepick.api.core.oauth2.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="OAuth2.0을 통해 가져올 Profile 모델")
public class OAuth2Profile {
    @Schema(description = "사용자 회원번호")
    private String clientId;

    @Schema(description = "사용자 닉네임")
    private String nickname;

    @Schema(description = "사용자 썸네일 이미지")
    private String thumbnailImageUrl;
}
