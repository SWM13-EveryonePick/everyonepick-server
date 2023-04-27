package soma.everyonepick.api.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;
import soma.everyonepick.api.core.exception.EveryonepickException;
import soma.everyonepick.api.user.dto.FaceInfoDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.UserRepository;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FaceInfoUploadServiceTest {

    @InjectMocks
    private FaceInfoUploadService faceInfoUploadService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ObjectMapper objectMapper;

    private User user;
    private MockMultipartFile image;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);

        byte[] imageBytes = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        image = new MockMultipartFile("image", "test.jpg", "image/jpeg", imageBytes);
    }

    @Test
    public void testUploadFaceInfoSuccess() throws JsonProcessingException {
        String responseBody = "{\"data\":{\"user_id\":1,\"face_embedding\":[0.1,0.2,0.3]}}";
        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), any(Class.class))).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));
        when(userRepository.saveAndFlush(user)).thenReturn(user);
        when(objectMapper.writeValueAsString(any())).thenReturn(responseBody);
        when(objectMapper.readTree(responseBody)).thenReturn(new ObjectMapper().readTree(responseBody));

        FaceInfoDto faceInfoDto = faceInfoUploadService.uploadFaceInfo(user, image);

        assertEquals(1L, faceInfoDto.getId());
        assertEquals(Arrays.asList(0.1, 0.2, 0.3), faceInfoDto.getFaceFeature());
    }

    @Test
    public void testUploadFaceInfoFailure() throws JsonProcessingException {
        when(objectMapper.writeValueAsString(any())).thenThrow(new EveryonepickException("AI server JSON 응답 파싱 에러"));
        EveryonepickException exception = assertThrows(EveryonepickException.class, () -> faceInfoUploadService.uploadFaceInfo(user, image));
        assertEquals("AI server JSON 응답 파싱 에러", exception.getMessage());
    }
}
