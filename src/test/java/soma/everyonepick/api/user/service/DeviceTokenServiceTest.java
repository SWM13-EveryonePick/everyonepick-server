package soma.everyonepick.api.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soma.everyonepick.api.user.dto.DeviceTokenDto;
import soma.everyonepick.api.user.entity.DeviceToken;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.DeviceTokenRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceTokenServiceTest {

    @Mock
    DeviceTokenRepository deviceTokenRepository;

    @InjectMocks
    DeviceTokenService deviceTokenService;

    User user;
    DeviceToken deviceToken;
    DeviceTokenDto deviceTokenDto;

    @BeforeEach
    void setUp() {
        user = new User();
        deviceToken = DeviceToken.builder()
                .user(user)
                .build();
        deviceTokenDto = new DeviceTokenDto();
        deviceTokenDto.setFcmDeviceToken("sample_token");
    }

    @Test
    void findDeviceTokenTest() {
        when(deviceTokenRepository.findByUserAndIsActive(any(User.class), any(Boolean.class)))
                .thenReturn(Optional.ofNullable(deviceToken));

        deviceTokenService.getDeviceToken(user);

        verify(deviceTokenRepository).findByUserAndIsActive(user, true);
    }

    @Test
    void createDeviceTokenTest() {
        when(deviceTokenRepository.findByUserAndIsActive(any(User.class), any(Boolean.class)))
                .thenReturn(Optional.empty());
        when(deviceTokenRepository.saveAndFlush(any(DeviceToken.class)))
                .thenReturn(deviceToken);

        deviceTokenService.createDeviceToken(user, deviceTokenDto);

        verify(deviceTokenRepository).saveAndFlush(any(DeviceToken.class));
    }
}
