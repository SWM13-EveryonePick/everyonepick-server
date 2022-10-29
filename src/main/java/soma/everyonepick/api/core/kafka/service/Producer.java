package soma.everyonepick.api.core.kafka.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import soma.everyonepick.api.album.dto.FaceSwapRequestDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {
    private static final String topicName = "everyonepick.faceswap.request";
    private final KafkaTemplate<String, FaceSwapRequestDto> kafkaTemplate;

    public void sendMessage(FaceSwapRequestDto message) {

        ListenableFuture<SendResult<String, FaceSwapRequestDto>> future =
                kafkaTemplate.send(topicName, message);

        future.addCallback(
                new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, FaceSwapRequestDto> result) {
                log.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        }
        );
    }
}
