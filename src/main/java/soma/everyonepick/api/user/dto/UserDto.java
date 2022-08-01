package soma.everyonepick.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "사용자 모델", description = "사용자 모델")
public class UserDto {

    @ApiModelProperty(value = "id", position = DisplayOrder.ID)
    @JsonProperty(index = DisplayOrder.ID)
    private Long id;

    @ApiModelProperty(value = "닉네임", position = DisplayOrder.NICKNAME)
    @JsonProperty(index = DisplayOrder.NICKNAME)
    private String nickname;

    @ApiModelProperty(value = "카카오 회원번호", position = DisplayOrder.KAKAO_CLIENT_ID)
    @JsonProperty(index = DisplayOrder.KAKAO_CLIENT_ID)
    private String clientId;

    @ApiModelProperty(value = "카카오 썸네일 이미지", position = DisplayOrder.THUMBNAIL_IMAGE_URL)
    @JsonProperty(index = DisplayOrder.THUMBNAIL_IMAGE_URL)
    private String thumbnailImageUrl;

    @ApiModelProperty(value = "푸쉬알림 동의 여부", position = DisplayOrder.IS_PUSH_ACTIVE)
    @JsonProperty(index = DisplayOrder.IS_PUSH_ACTIVE)
    private Boolean isPushActive;



    private static class DisplayOrder {
        private static final int ID = 0;
        private static final int NICKNAME = 1;
        private static final int KAKAO_CLIENT_ID = 2;
        private static final int THUMBNAIL_IMAGE_URL = 3;
        private static final int IS_PUSH_ACTIVE = 4;
    }
}


