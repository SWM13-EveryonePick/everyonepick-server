package soma.everyonepick.api.album.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "단체앨범 수정모델")
public class GroupAlbumUpdateDto {
    @Schema(description = "제목")
    @JsonProperty(index = 0)
    private String title;
}
