package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import soma.everyonepick.api.user.dto.UserRequestDto;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "단체앨범 사용자 모델")
public class GroupAlbumUserDto {

    @Schema(description = "단체앨범 멤버")
    @JsonProperty(index = 0)
    private List<UserRequestDto> users;
}