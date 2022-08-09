package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.user.entity.User;

import java.util.List;

public interface UserGroupAlbumRepository extends JpaRepository<UserGroupAlbum, Long> {
    /**
     * UserGroupAlbum 목록을 groupAlbum과 활성화 여부로 검색
     * @param groupAlbum 단체앨범 엔티티
     * @param isActive 활성화 여부
     * @return UserGroupAlbum 리스트
     */
    List<UserGroupAlbum> findAllByGroupAlbumAndIsActive(GroupAlbum groupAlbum, boolean isActive);
}
