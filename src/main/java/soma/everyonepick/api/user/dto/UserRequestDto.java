package soma.everyonepick.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 요청모델")
public class UserRequestDto {

    @Schema(description = "카카오 회원 아이디")
    @JsonProperty(index = 0)
    private String clientId;
}
