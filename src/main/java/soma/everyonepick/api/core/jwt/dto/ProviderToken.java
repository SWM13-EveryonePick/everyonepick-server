package soma.everyonepick.api.core.jwt.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description="Provider Token Dto")
public class ProviderToken {
    @Schema(description = "프로바이더 이름 (현재는 KAKAO만 가능)")
    private String providerName;

    @Schema(description = "Provider 제공 Access Token")
    private String providerAccessToken;

//    public void setProviderName(String providerName) {
//        this.providerName = Provider.create(providerName);
//    }
}
