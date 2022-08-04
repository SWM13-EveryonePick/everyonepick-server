package soma.everyonepick.api.core.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Refresh Token")
public class RefreshDto {
    @Schema(description = "Everyonepick 제공 Refresh Token")
    private String everyonepickRefreshToken;
}
