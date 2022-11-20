package soma.everyonepick.api.core.fcm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.user.entity.User;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "FCM 메시지 모델", hidden = true)
public class PushMessage {
    @Schema(description = "메시지 전달 대상", hidden = true)
    @JsonProperty(index = 0)
    private List<User> users;

    @Schema(description = "멤버가 속한 단체앨범", hidden = true)
    @JsonProperty(index = 1)
    private GroupAlbum groupAlbum;

    @Schema(description = "메시지 데이터", hidden = true)
    @JsonProperty(index = 2)
    private MessageData messageData;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "푸시 메시지 데이터 모델", hidden = true)
    public static class MessageData {
        @Schema(description = "제목", hidden = true)
        @JsonProperty(index = 0)
        private String title;

        @Schema(description = "내용", hidden = true)
        @JsonProperty(index = 1)
        private String content;
    }
}
