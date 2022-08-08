package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;

public interface GroupAlbumRepository extends JpaRepository<GroupAlbum, Long> {
}
