package soma.everyonepick.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.user.entity.RefreshToken;
import soma.everyonepick.api.user.entity.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    /**
     * 사용자로 리프레시 토큰 찾기
     * @param user 사용자
     * @return 리프레시 토큰 Optional
     */
    Optional<RefreshToken> findByUser(User user);
}
