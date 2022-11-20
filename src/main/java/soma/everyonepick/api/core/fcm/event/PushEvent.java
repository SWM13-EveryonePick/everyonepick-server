package soma.everyonepick.api.core.fcm.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import soma.everyonepick.api.core.fcm.dto.PushMessage;

@AllArgsConstructor
@Getter
public class PushEvent {
    private PushMessage pushMessage;
}
