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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserGroupAlbumService {
    private static final String KAKAO_IDENTIFIER_PREFIX = "kakao_";
    private final UserGroupAlbumRepository userGroupAlbumRepository;
    private final UserService userService;

    /**
     * 단체앨범에 속한 맴버들을 groupAlbum로 조회
     * @param groupAlbum 단체앨범 엔티티
     * @return List<User> 사용자 리스트
     */
    @Transactional(readOnly = true)
    public List<User> getMembers(GroupAlbum groupAlbum) {
        List<UserGroupAlbum> userGroupAlbums = userGroupAlbumRepository.findAllByGroupAlbumAndIsActive(groupAlbum, true);
        List<User> users = userGroupAlbums.stream().map(UserGroupAlbum::getUser).collect(Collectors.toList());
        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());

        if (!userIds.contains(groupAlbum.getHostUserId())) {
            throw new BadRequestException(NOT_EXIST_HOST);
        }
        return users;
    }

    /**
     * 현재 로그인한 사용자의 단체앨범 목록 조회
     * @param user 현재 로그인한 사용자 엔티티
     * @return List<GroupAlbum> 단체앨범 리스트
     */
    @Transactional(readOnly = true)
    public List<GroupAlbum> getMyGroupAlbums(User user) {
        List<UserGroupAlbum> userGroupAlbums = userGroupAlbumRepository.findAllByUserAndIsActiveOrderByCreatedAtDesc(user, true);
        return userGroupAlbums.stream()
                .map(UserGroupAlbum::getGroupAlbum)
                .collect(Collectors.toList());
    }

    /**
     * 현재 로그인한 사용자의 UserGroupAlbum 조회
     * @param user 현재 로그인한 사용자 엔티티
     * @param groupAlbum 단체앨범 엔티티
     * @return UserGroupAlbum 없으면 null
     */
    @Transactional(readOnly = true)
    public UserGroupAlbum getUserGroupAlbum(User user, GroupAlbum groupAlbum) {
        return userGroupAlbumRepository.findByUserAndGroupAlbumAndIsActive(user, groupAlbum, true)
                .orElse(null);
    }

    /**
     * 멤버들을 단체앨범에 등록
     * @param clientIds 멤버들의 회원아이디 리스트
     * @param groupAlbum 단체앨범 엔티티
     * @return GroupAlbum 단체앨범 엔티티
     */
    @Transactional
    public GroupAlbum registerUsers(User user, List<String> clientIds, GroupAlbum groupAlbum) {
        if (groupAlbum.getHostUserId() != user.getId()) {
            throw new BadRequestException(NOT_HOST);
        }

        List<User> users = clientIds.stream()
                .map(s -> userService.findByClientId(KAKAO_IDENTIFIER_PREFIX + s))
                .collect(Collectors.toList());

        if (getUserGroupAlbum(user, groupAlbum) == null) {
            users.add(user);
        }

        List<UserGroupAlbum> userGroupAlbums = new ArrayList<>();

        for (User member: users) {
            if (getUserGroupAlbum(member, groupAlbum) != null) {
                throw new BadRequestException(REDUNDANT_USER_GROUP_ALBUM);
            }
            userGroupAlbums.add(
                    UserGroupAlbum.builder()
                            .user(member)
                            .groupAlbum(groupAlbum)
                            .build()
            );
        }
        userGroupAlbumRepository.saveAllAndFlush(userGroupAlbums);
        return groupAlbum;
    }

    /**
     * 멤버들을 단체앨범에서 강퇴
     * @param clientIds 멤버들의 회원아이디 리스트
     * @param groupAlbum 단체앨범 엔티티
     * @return GroupAlbum 단체앨범 엔티티
     */
    @Transactional
    public GroupAlbum banUsers(User user, List<String> clientIds, GroupAlbum groupAlbum) {
        if (groupAlbum.getHostUserId() != user.getId()) {
            throw new BadRequestException(NOT_HOST);
        }

        List<User> users = clientIds.stream()
                .map(s -> userService.findByClientId(KAKAO_IDENTIFIER_PREFIX + s))
                .collect(Collectors.toList());

        List<UserGroupAlbum> userGroupAlbums = new ArrayList<>();

        for (User member : users) {
            userGroupAlbums.add(getUserGroupAlbum(member, groupAlbum));
        }
        userGroupAlbumRepository.deleteAll(userGroupAlbums);
        return groupAlbum;
    }

    /**
     * 현재 단체앨범의 UserGroupAlbum 삭제
     * @param user 현재 로그인한 사용자 엔티티
     * @param groupAlbum 단체앨범 엔티티
     * @return UserGroupAlbum
     */
    @Transactional
    public UserGroupAlbum deleteUserGroupAlbum(User user, GroupAlbum groupAlbum) {
        UserGroupAlbum userGroupAlbum = getUserGroupAlbum(user, groupAlbum);
        if (groupAlbum.getHostUserId() == user.getId()) {
            throw new BadRequestException(HOST_MUST_STAY);
        }
        userGroupAlbumRepository.delete(userGroupAlbum);
        return userGroupAlbum;
    }
}
