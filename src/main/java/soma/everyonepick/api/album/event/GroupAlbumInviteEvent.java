package soma.everyonepick.api.album.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soma.everyonepick.api.core.fcm.dto.PushMessage;

@AllArgsConstructor
@Getter
public class GroupAlbumInviteEvent {
    private PushMessage pushMessage;
}
