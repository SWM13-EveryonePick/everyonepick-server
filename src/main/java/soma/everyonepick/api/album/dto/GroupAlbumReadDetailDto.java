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
@Schema(description = "단체앨범 상세 읽기 모델")
public class GroupAlbumReadDetailDto {
    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Schema(description = "제목")
    @JsonProperty(index = 1)
    private String title;

    @Schema(description = "방장")
    @JsonProperty(index = 2)
    private Long hostUserId;

    @Schema(description = "단체앨범 멤버")
    @JsonProperty(index = 3)
    private List<UserDto> users;

    @Schema(description = "단체앨범 사진")
    @JsonProperty(index = 4)
    private List<PhotoDto> photos;
}
