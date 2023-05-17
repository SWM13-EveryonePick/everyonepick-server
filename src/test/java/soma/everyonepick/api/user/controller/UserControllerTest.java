package soma.everyonepick.api.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import soma.everyonepick.api.annotation.WithMockEveryonepickUser;
import soma.everyonepick.api.core.config.SecurityConfig;
import soma.everyonepick.api.user.dto.DeviceTokenDto;
import soma.everyonepick.api.user.dto.FaceInfoDto;
import soma.everyonepick.api.user.entity.DeviceToken;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.DeviceTokenService;
import soma.everyonepick.api.user.service.FaceInfoUploadService;
import soma.everyonepick.api.user.service.UserService;


import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@ActiveProfiles("local")
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DeviceTokenService deviceTokenService;

    @MockBean
    private FaceInfoUploadService faceInfoUploadService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = User.builder()
                .clientId("testClientId")
                .build();
    }

    @Test
    @WithMockEveryonepickUser
    public void getUserByClientIdTest() throws Exception {
        given(userService.findByClientId("testClientId")).willReturn(testUser);

        mockMvc.perform(get("/api/v1/users")
                        .param("clientId", "testClientId"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockEveryonepickUser
    public void getUserByIdTest() throws Exception {
        given(userService.findById(anyLong())).willReturn(testUser);

        mockMvc.perform(get("/api/v1/users/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.clientId").value("testClientId"));
    }

    @Test
    @WithMockEveryonepickUser
    public void getUserTest() throws Exception {
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.clientId").value("testClientId"));
    }

    @Test
    @WithMockEveryonepickUser
    public void postFaceInfoTest() throws Exception {
        FaceInfoDto faceInfoDto = FaceInfoDto.builder()
                .faceFeature(new ArrayList<>(Arrays.asList(1.0, 2.0, 3.0)))
                .build();
        MockMultipartFile multipartFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());

        given(faceInfoUploadService.uploadFaceInfo(testUser, multipartFile)).willReturn(faceInfoDto);

        mockMvc.perform(multipart("/api/v1/users/face-info")
                        .file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockEveryonepickUser
    public void postDeviceTokenTest() throws Exception {
        DeviceTokenDto deviceTokenDto = new DeviceTokenDto();
        deviceTokenDto.setFcmDeviceToken("testDeviceToken");
        given(deviceTokenService.createDeviceToken(any(User.class), any(DeviceTokenDto.class)))
                .willReturn(new DeviceToken());

        mockMvc.perform(post("/api/v1/users/device-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceTokenDto)))
                .andExpect(status().isOk());
    }
}
