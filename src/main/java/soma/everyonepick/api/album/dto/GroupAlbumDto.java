package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import soma.everyonepick.api.user.dto.UserDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "단체앨범 모델")
public class GroupAlbumDto {
    @Schema(description = "제목")
    @JsonProperty(index = 0)
    private String title;

    @Schema(description = "단체앨범의 회원아이디 리스트")
    @JsonProperty(index = 1)
    private List<UserDto> users;
}
