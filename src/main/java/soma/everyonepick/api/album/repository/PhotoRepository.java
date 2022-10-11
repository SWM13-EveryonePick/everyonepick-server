package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    /**
     * 사진 id와 활성화 여부로 사진 조회
     * @param photoId
     * @param isActive
     * @return Optional<Photo>
     */
    Optional<Photo> findByIdAndIsActive(Long photoId, boolean isActive);

    /**
     * 단체앨범 엔티티와 활성화 여부로 사진 목록 조회
     * @param groupAlbum
     * @param isActive
     * @return List<Photo>
     */
    List<Photo> findAllByGroupAlbumAndIsActiveOrderByCreatedAtDesc(GroupAlbum groupAlbum, boolean isActive);
}
