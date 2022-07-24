package soma.everyonepick.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
