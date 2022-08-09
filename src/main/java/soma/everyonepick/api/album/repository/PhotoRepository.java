package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    /**
     * 단체앨범 엔티티와 활성화 여부로 사진 목록 조회
     * @param groupAlbum
     * @param isActive
     * @return List<Photo>
     */
    List<Photo> findAllByGroupAlbumAndIsActive(GroupAlbum groupAlbum, boolean isActive);
}
