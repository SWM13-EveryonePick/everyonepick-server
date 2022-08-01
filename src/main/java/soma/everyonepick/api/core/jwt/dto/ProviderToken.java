package soma.everyonepick.api.core.jwt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value="Provider Token Dto", description="Provider 토큰 모델")
public class ProviderToken {
    @ApiModelProperty(value = "프로바이더 이름 (현재는 KAKAO만 가능)")
    private Provider providerName;

    @ApiModelProperty(value = "Provider 제공 Access Token")
    private String providerAccessToken;

    public void setProviderName(String providerName) {
        this.providerName = Provider.create(providerName);
    }
}
