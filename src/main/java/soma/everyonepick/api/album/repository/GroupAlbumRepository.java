package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;

import java.util.Optional;

public interface GroupAlbumRepository extends JpaRepository<GroupAlbum, Long> {
    /**
     * 단체앨범을 groupAlbumId와 활성화 여부로 검색
     * @param groupAlbumId 단체앨범 id
     * @param isActive 활성화 여부
     * @return 단체앨범 Optional
     */
    Optional<GroupAlbum> findByIdAndIsActive(Long groupAlbumId, boolean isActive);
}
