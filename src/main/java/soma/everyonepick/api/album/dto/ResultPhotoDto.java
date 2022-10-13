package soma.everyonepick.api.album.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "합성완료사진 모델")
public class ResultPhotoDto {
    @Schema(description = "id")
    @JsonProperty(index = 0)
    private Long id;

    @Getter
    @Setter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "합성완료사진 응답 모델")
    public static class ResultPhotoResponseDto extends ResultPhotoDto{
        @Schema(description = "합성완료사진 URL")
        @JsonProperty(index = 1)
        private String resultPhotoUrl;
    }


}
