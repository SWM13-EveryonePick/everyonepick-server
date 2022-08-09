package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.album.repository.UserGroupAlbumRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupAlbumService {
    private final UserGroupAlbumRepository userGroupAlbumRepository;
    private final UserService userService;

    /**
     * 단체앨범에 속한 맴버들을 groupAlbum로 조회
     * @param groupAlbum 단체앨범 엔티티
     * @return List<User> 사용자 리스트
     */
    @Transactional
    public List<User> getMembers(GroupAlbum groupAlbum) {
        List<UserGroupAlbum> userGroupAlbums = userGroupAlbumRepository.findAllByGroupAlbumAndIsActive(groupAlbum, true);
        List<User> users = userGroupAlbums.stream().map(UserGroupAlbum::getUser).collect(Collectors.toList());
        User hostUser = userService.findById(groupAlbum.getHostUserId());
        if (!users.contains(hostUser)) {
            throw new BadRequestException("멤버에 방장이 포함되어있지 않습니다.");
        }
        users.set(0, hostUser);
        return users;
    }

    /**
     * 현재 로그인한 사용자의 단체앨범 목록 조회
     * @param user 현재 로그인한 사용자 엔티티
     * @return List<GroupAlbum> 단체앨범 리스트
     */
    @Transactional
    public List<GroupAlbum> getMyGroupAlbums(User user) {
        List<UserGroupAlbum> userGroupAlbums = userGroupAlbumRepository.findAllByUserAndIsActiveOrderByCreatedAtDesc(user, true);
        return userGroupAlbums.stream()
                .map(UserGroupAlbum::getGroupAlbum)
                .collect(Collectors.toList());
    }
}
