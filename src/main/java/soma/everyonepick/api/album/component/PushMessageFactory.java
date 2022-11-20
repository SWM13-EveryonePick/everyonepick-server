package soma.everyonepick.api.album.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.core.fcm.dto.PushMessage;
import soma.everyonepick.api.user.entity.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PushMessageFactory {
    /**
     * PushMessage 데이터를 구성한다.
     * @param users 전송 대상 사용자들
     * @param groupAlbum 단체앨범
     * @param title 메시지 제목
     * @param content 메시지 내용
     * @return
     */
    public PushMessage buildPushMessage(List<User> users, GroupAlbum groupAlbum, String title, String content) {
        return PushMessage.builder()
                .users(users)
                .groupAlbum(groupAlbum)
                .messageData(
                        PushMessage.MessageData.builder()
                                .title(title)
                                .content(content)
                                .build()
                ).build();
    }
}
