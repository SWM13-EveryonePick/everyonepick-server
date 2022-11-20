package soma.everyonepick.api.core.fcm.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.core.fcm.service.FirebaseCloudMessageService;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableAsync
public class PushEventListener {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Async
    @EventListener
    public void sendPush(PushEvent pushEvent) {
        log.info("Listened PushEvent");
        firebaseCloudMessageService.sendPushToGroup(pushEvent.getPushMessage());
    }
}
