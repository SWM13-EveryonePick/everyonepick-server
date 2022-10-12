package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.ResultPhoto;

import java.util.List;

public interface ResultPhotoRepository extends JpaRepository<ResultPhoto, Long> {
    /**
     * 단체앨범 엔티티와 활성화 여부로 합성완료 사진 목록 조회
     * @param groupAlbum
     * @param isActive
     * @return List<ResultPhoto>
     */
    List<ResultPhoto> findAllByGroupAlbumAndIsActiveOrderByCreatedAtDesc(GroupAlbum groupAlbum, boolean isActive);
}
