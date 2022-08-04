package soma.everyonepick.api.core.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.core.oauth2.dto.OAuth2Profile;

/**
 * 카카오 OAuth2로부터 조회할 수 있는 서비스
 */
@RequiredArgsConstructor
@Service
public class KakaoOAuth2Service implements OAuth2Service {
    private static final String KAKAO_PROFILE_URL = "https://kapi.kakao.com/v2/user/me";
    private static final String KAKAO_IDENTIFIER_PREFIX = "kakao_";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /**
     * 프로바이더로부터 {@link OAuth2Profile}를 조회한다.
     * @param providerAccessToken 프로바이더 제공 토큰
     * @return 프로바이더에서 가져온 사용자 프로필
     */
    public OAuth2Profile getProfile(String providerAccessToken) {
        JsonNode profileJson = requestProfile(providerAccessToken);
        return buildKakaoProfile(profileJson);
    }

    private JsonNode requestProfile(String providerAccessToken) throws HttpClientErrorException {
        ResponseEntity<String> response = restTemplate.postForEntity(
                KAKAO_PROFILE_URL,
                buildRequest(providerAccessToken),
                String.class
        );

        try {
            return objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new EveryonepickException("Provider 응답 호출 중 예외 발생", e);
        }
    }

    private HttpEntity<?> buildRequest(String providerAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(providerAccessToken);

        return new HttpEntity<>(null, headers);
    }

    private OAuth2Profile buildKakaoProfile(JsonNode jsonNode) {
        Long clientId = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("kakao_account").get("profile").get("nickname").asText();
        String thumbnailUrl = jsonNode.get("kakao_account").get("profile").get("thumbnail_image_url").asText();

        return new OAuth2Profile(KAKAO_IDENTIFIER_PREFIX + clientId.toString(), nickname, thumbnailUrl);
    }
}
