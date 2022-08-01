package soma.everyonepick.api.core.jwt.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ApiModel(value = "Everyonepick Jwt Dto", description = "Everyonepick 제공 JWT Access Token, Refresh Token")
public class Jwt {
    @ApiModelProperty(value = "Everyonepick 제공 Access Token")
    private String everyonepickAccessToken;

    @ApiModelProperty(value = "Everyonepick 제공 Refresh Token")
    private String everyonepickRefreshToken;
}
