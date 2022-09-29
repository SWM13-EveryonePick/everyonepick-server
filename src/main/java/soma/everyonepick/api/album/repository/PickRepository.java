package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;

import java.util.List;
import java.util.Optional;

public interface PickRepository extends JpaRepository<Pick, Long> {

    /**
     * 사진 id와 활성화 여부로 사진선택 작업 조회
     * @param pickId
     * @param isActive
     * @return Optional<Pick>
     */
    Optional<Pick> findByIdAndIsActive(Long pickId, boolean isActive);

    /**
     * 단체앨범 엔티티와 활성화 여부로 사진선택 작업 목록 조회
     * @param groupAlbum
     * @param isActive
     * @return List<Pick>
     */
    List<Pick> findAllByGroupAlbumAndIsActive(GroupAlbum groupAlbum, boolean isActive);
}
