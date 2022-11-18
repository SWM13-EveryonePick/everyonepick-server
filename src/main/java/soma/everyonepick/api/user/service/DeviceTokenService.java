package soma.everyonepick.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.user.dto.DeviceTokenDto;
import soma.everyonepick.api.user.entity.DeviceToken;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.DeviceTokenRepository;

@Service
@RequiredArgsConstructor
public class DeviceTokenService {
    private final DeviceTokenRepository deviceTokenRepository;

    /**
     * 사용자의 DeviceToken 찾기
     * @param user 사용자 엔티티
     * @return DeviceToken
     */
    @Transactional(readOnly = true)
    public DeviceToken getDeviceToken(User user) {
        return deviceTokenRepository.findByUserAndIsActive(user, true).orElse(null);
    }

    /**
     * 사용자의 DeviceToken을 갱신한다.
     * @param user 사용자
     * @param deviceTokenDto
     * @return DeviceToken
     */
    @Transactional
    public DeviceToken createDeviceToken(User user, DeviceTokenDto deviceTokenDto) {
        String fcmDeviceToken = deviceTokenDto.getFcmDeviceToken();

        DeviceToken deviceToken = getDeviceToken(user);

        if (deviceToken == null) {
            deviceToken = DeviceToken.builder()
                    .user(user)
                    .build();
        }

        deviceToken.setFcmDeviceToken(fcmDeviceToken);

        return deviceTokenRepository.saveAndFlush(deviceToken);
    }
}
