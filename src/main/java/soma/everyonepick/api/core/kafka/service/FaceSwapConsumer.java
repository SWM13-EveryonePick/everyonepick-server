package soma.everyonepick.api.core.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import soma.everyonepick.api.album.component.ResultPhotoMapper;
import soma.everyonepick.api.album.dto.FaceSwapResultDto;
import soma.everyonepick.api.album.dto.ResultPhotoDto;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.repository.PickRepository;
import soma.everyonepick.api.album.repository.ResultPhotoRepository;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.album.service.ResultPhotoUploadService;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_PICK;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceSwapConsumer {
    private final ResultPhotoUploadService resultPhotoUploadService;
    private final PickRepository pickRepository;
    private final ResultPhotoMapper resultPhotoMapper;

    /**
     * 합성결과를 Kafka Broker로부터 가져와서 ResultPhotoDto를 생성
     * @param faceSwapResultDto
     * @return ResultPhotoResponseDto
     */
    @KafkaListener(
            topics = "everyonepick.faceswap.result",
            groupId = "face-swap-1",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public ResultPhotoDto.ResultPhotoResponseDto listener(FaceSwapResultDto faceSwapResultDto) {
        log.info("Listener 동작: " + faceSwapResultDto.getPick_id().toString());
        return buildResultPhotoDto(faceSwapResultDto);
    }

    private ResultPhotoDto.ResultPhotoResponseDto buildResultPhotoDto(FaceSwapResultDto faceSwapResultDto) {
        Pick pick = pickRepository.findById(faceSwapResultDto.getPick_id())
                .orElseThrow(()-> new ResourceNotFoundException(NOT_EXIST_PICK));
        String image = faceSwapResultDto.getFace_swap_result();

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytesImage = decoder.decode(image);

        return resultPhotoMapper.toResponseDto(
                resultPhotoUploadService.uploadResultPhoto(new ByteArrayInputStream(bytesImage), pick)
        );
    }
}
