package soma.everyonepick.api.album.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisKeyExpiredEventListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("Received message : " + message.toString());

    }
}
