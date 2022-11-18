package soma.everyonepick.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.user.entity.DeviceToken;
import soma.everyonepick.api.user.entity.User;

import java.util.Optional;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, Long> {
    /**
     * 사용자의 DeviceToken 찾기
     * @param user 사용자 엔티티
     * @param isActive 활성화 여부
     * @return Optional<DeviceToken>
     */
    Optional<DeviceToken> findByUserAndIsActive(User user, boolean isActive);
}
