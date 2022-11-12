package soma.everyonepick.api.album.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.component.FaceSwapRequestDtoFactory;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.service.PickService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisKeyExpiredEventListener implements MessageListener {
    private final PickService pickService;
    private final FaceSwapRequestDtoFactory faceSwapRequestDtoFactory;
    private final ApplicationEventPublisher publisher;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        log.info("Received message : " + key);

        log.info("PickId: " + key.split(":")[1]);
        Pick pick = pickService.getPickById(Long.valueOf(key.split(":")[1]));


        publisher.publishEvent(
                new FaceSwapRequestEvent(
                        faceSwapRequestDtoFactory.buildFaceSwapRequestDto(pick)
                )
        );
        log.info("Publish Event");
    }
}
