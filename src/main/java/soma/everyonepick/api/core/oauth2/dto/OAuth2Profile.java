package soma.everyonepick.api.core.oauth2.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="OAuth2.0 Profile", description="OAuth2.0을 통해 가져올 Profile 모델")
public class OAuth2Profile {
    @ApiModelProperty(value = "사용자 회원번호")
    private String clientId;

    @ApiModelProperty(value = "사용자 닉네임")
    private String nickname;

    @ApiModelProperty(value = "사용자 썸네일 이미지")
    private String thumbnailImageUrl;
}
