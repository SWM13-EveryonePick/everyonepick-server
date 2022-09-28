package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.PickPhoto;

public interface PickPhotoRepository extends JpaRepository<PickPhoto, Long> {
}
