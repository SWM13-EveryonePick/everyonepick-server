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
@Schema(description = "단체앨범 목록 생성 모델")
public class GroupAlbumCreateDto {
    @Schema(description = "제목")
    @JsonProperty(index = 0)
    private String title;

    @Schema(description = "단체앨범에 초대할 친구들의 회원아이디 리스트")
    @JsonProperty(index = 1)
    private List<UserDto> users;
}
