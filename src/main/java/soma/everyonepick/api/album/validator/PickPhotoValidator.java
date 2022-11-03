package soma.everyonepick.api.album.validator;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import soma.everyonepick.api.album.component.PhotoMapper;
import soma.everyonepick.api.album.dto.FaceValidateDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.core.handler.RestTemplateResponseErrorHandler;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PickPhotoValidator {

    private final String aiServerUrl = "http://ec2-43-200-70-173.ap-northeast-2.compute.amazonaws.com:8080/api/detect";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final RestTemplateResponseErrorHandler responseErrorHandler;
    private final PhotoMapper photoMapper;
    private final UserGroupAlbumService userGroupAlbumService;

    /**
     * PickPhoto로 등록될 이미지들을 AI 서버로 전달하여 검증한다.
     * @param groupAlbum 단체앨범 엔티티
     * @param photos 사진선택 작업에 포함될 사진들
     * @return Boolean
     */
    public Boolean pickPhotoValidator(GroupAlbum groupAlbum, List<Photo> photos) {
        HttpEntity<String> requestEntity = buildRequest(
                (long)userGroupAlbumService.getMembers(groupAlbum).size(),
                photos
        );
        restTemplate.setErrorHandler(responseErrorHandler);
        ResponseEntity<String> response = restTemplate.postForEntity(aiServerUrl, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Success: " + response.getBody());
            return true;
        }
        return false;
    }

    private HttpEntity<String> buildRequest(Long userCnt, List<Photo> photos) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        try {
            FaceValidateDto faceValidateDto = FaceValidateDto.builder()
                    .userCnt(userCnt)
                    .photos(
                            photos.stream()
                                    .map(photoMapper::toDto)
                                    .collect(Collectors.toList())
                    )
                    .build();

            return new HttpEntity<>(objectMapper.writeValueAsString(faceValidateDto), headers);
        } catch (IOException ioException) {
            throw new EveryonepickException(ioException);
        }
    }
}
