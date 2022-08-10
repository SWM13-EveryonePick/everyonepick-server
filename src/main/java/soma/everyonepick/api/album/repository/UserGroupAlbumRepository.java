package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserGroupAlbumRepository extends JpaRepository<UserGroupAlbum, Long> {
    /**
     * UserGroupAlbum 목록을 groupAlbum과 활성화 여부로 검색
     * @param groupAlbum 단체앨범 엔티티
     * @param isActive 활성화 여부
     * @return UserGroupAlbum 리스트
     */
    List<UserGroupAlbum> findAllByGroupAlbumAndIsActive(GroupAlbum groupAlbum, boolean isActive);

    /**
     * UserGroupAlbum 목록을 User와 활성화 여부로 검색
     * @param user 현재 로그인한 사용자
     * @param isActive 활성화 여부
     * @return UserGroupAlbum 리스트
     */
    List<UserGroupAlbum> findAllByUserAndIsActiveOrderByCreatedAtDesc(User user, boolean isActive);

    /**
     * UserGroupAlbum을 User와 단체앨범과 활성화 여부로 검색
     * @param user 현재 로그인한 사용자
     * @param groupAlbum 단체앨범 엔티티
     * @param isActive 활성화 여부
     * @return Optional<UserGroupAlbum>
     */
    Optional<UserGroupAlbum> findByUserAndGroupAlbumAndIsActive(User user, GroupAlbum groupAlbum, boolean isActive);
}
