package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import soma.everyonepick.api.user.dto.UserRequestDto;
import soma.everyonepick.api.user.dto.UserResponseDto;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "단체앨범 요청모델")
public class GroupAlbumRequestDto {
    @Schema(description = "제목")
    @JsonProperty(index = 0)
    private String title;

    @Schema(description = "단체앨범 멤버")
    @JsonProperty(index = 1)
    private List<UserRequestDto> users;
}
