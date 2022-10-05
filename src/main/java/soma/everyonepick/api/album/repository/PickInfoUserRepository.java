package soma.everyonepick.api.album.repository;

import org.springframework.data.repository.CrudRepository;
import soma.everyonepick.api.album.entity.PickInfoUser;

public interface PickInfoUserRepository extends CrudRepository<PickInfoUser, String> {
}
