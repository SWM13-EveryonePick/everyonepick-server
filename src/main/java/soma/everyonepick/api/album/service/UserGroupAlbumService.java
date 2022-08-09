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
     * 단체앨범에 속한 맴버들을 groupAlbum과 활성화 여부로 검색
     * @param groupAlbum 단체앨범 엔티티
     * @return 사용자 리스트
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
}
