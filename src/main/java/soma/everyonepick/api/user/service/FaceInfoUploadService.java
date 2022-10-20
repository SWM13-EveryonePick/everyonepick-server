package soma.everyonepick.api.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.user.dto.FaceInfoDto;
import soma.everyonepick.api.user.entity.User;

@Service
@RequiredArgsConstructor
public class FaceInfoUploadService {

    private final String aiServerUrl = "";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 이미지를 AI 서버로 전달하고 user embedding data를 받는다.
     * @param user 로그인한 사용자
     * @param image Multipart File
     */
    public FaceInfoDto uploadFaceInfo(User user, MultipartFile image) {
        HttpEntity<MultiValueMap<String, Object>> requestEntity = buildRequest(user.getId(), image);
        ResponseEntity<String> response = restTemplate.postForEntity(aiServerUrl, requestEntity, String.class);

        try {
            return buildFaceInfoDto(objectMapper.readTree(response.getBody()));
        } catch (JsonProcessingException e) {
            throw new EveryonepickException("AI server JSON 응답 파싱 에러", e);
        }
    }

    private HttpEntity<MultiValueMap<String, Object>> buildRequest(Long userId, MultipartFile image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("user_id", userId.toString());
        body.add("image", image);

        return new HttpEntity<>(body, headers);
    }

    private FaceInfoDto buildFaceInfoDto(JsonNode data) {
        Long userId = data.get("id").asLong();
        String faceFeature = data.get("face_embedding").asText();

        return  FaceInfoDto.builder()
                .id(userId)
                .faceFeature(faceFeature)
                .build();
    }
}
