package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.UserGroupAlbum;

public interface UserGroupAlbumRepository extends JpaRepository<UserGroupAlbum, Long> {
}
