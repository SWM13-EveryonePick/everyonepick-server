package soma.everyonepick.api.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.user.dto.FaceInfoDto;
import soma.everyonepick.api.user.dto.FaceInfoRequestDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.core.handler.RestTemplateResponseErrorHandler;
import soma.everyonepick.api.user.repository.UserRepository;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceInfoUploadService {

    private final String aiServerUrl = "http://ec2-43-200-70-173.ap-northeast-2.compute.amazonaws.com:8080/api/recognize";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RestTemplateResponseErrorHandler responseErrorHandler;
    private final UserRepository userRepository;

    /**
     * 이미지를 base64로 인코딩하여 문자열로 AI 서버로 전달하고 user embedding data를 받는다.
     * @param user 로그인한 사용자
     * @param image Multipart File
     */
    @Transactional
    public FaceInfoDto uploadFaceInfo(User user, MultipartFile image) {
        HttpEntity<String> requestEntity = buildRequest(user.getId(), image);
        restTemplate.setErrorHandler(responseErrorHandler);
        ResponseEntity<String> response = restTemplate.postForEntity(aiServerUrl, requestEntity, String.class);

        try {
            user.setIsRegistered(true);
            userRepository.saveAndFlush(user);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
            return buildFaceInfoDto(objectMapper.readTree(response.getBody()));
        } catch (JsonProcessingException e) {
            throw new EveryonepickException("AI server JSON 응답 파싱 에러", e);
        }
    }

    private HttpEntity<String> buildRequest(Long userId, MultipartFile image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        try {
            String encodeBase64 = Base64.getEncoder().encodeToString(image.getBytes());

            FaceInfoRequestDto faceInfoRequestDto = FaceInfoRequestDto.builder()
                    .userId(userId)
                    .image(encodeBase64)
                    .build();

            return new HttpEntity<>(objectMapper.writeValueAsString(faceInfoRequestDto), headers);
        } catch (IOException ioException) {
            throw new EveryonepickException(ioException);
        }
    }

    private FaceInfoDto buildFaceInfoDto(JsonNode data) {
        Long userId = data
                .get("data")
                .get("user_id")
                .asLong();

        List<Double> faceFeature = new ArrayList<>();
        Iterator<JsonNode> iterator = data.get("data").get("face_embedding").elements();

        while (iterator.hasNext()) {
            faceFeature.add(iterator.next().doubleValue());
        }

        return  FaceInfoDto.builder()
                .id(userId)
                .faceFeature(faceFeature)
                .build();
    }
}
