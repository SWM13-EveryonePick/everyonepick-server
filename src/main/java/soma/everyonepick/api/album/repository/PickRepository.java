package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.Pick;

public interface PickRepository extends JpaRepository<Pick, Long> {
}
