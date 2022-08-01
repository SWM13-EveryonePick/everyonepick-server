package soma.everyonepick.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 사용자를 식별자와 활성화 여부로 검색
     * @param clientId 사용자 회원번호
     * @param isActive 활성화 여부
     * @return 사용자 Optional
     */
    Optional<User> findByClientIdAndIsActive(String clientId, boolean isActive);
}
