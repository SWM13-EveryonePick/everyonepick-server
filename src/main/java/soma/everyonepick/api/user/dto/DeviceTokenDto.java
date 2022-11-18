package soma.everyonepick.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "FCM Device Token 모델")
public class DeviceTokenDto {
    @Schema(description = "FCM device token")
    @JsonProperty(index = 0)
    private String fcmDeviceToken;
}
