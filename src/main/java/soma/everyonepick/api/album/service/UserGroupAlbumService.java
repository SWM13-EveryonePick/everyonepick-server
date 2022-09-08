package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.album.repository.GroupAlbumRepository;
import soma.everyonepick.api.album.repository.UserGroupAlbumRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserGroupAlbumService {
    private final UserGroupAlbumRepository userGroupAlbumRepository;
    private final GroupAlbumRepository groupAlbumRepository;

    /**
     * 단체앨범에 속한 맴버들을 groupAlbum로 조회
     * @param groupAlbum 단체앨범 Entity
     * @return List<User> 사용자 리스트
     */
    @Transactional(readOnly = true)
    public List<User> getMembers(GroupAlbum groupAlbum) {
        List<UserGroupAlbum> userGroupAlbums = userGroupAlbumRepository.findAllByGroupAlbumAndIsActiveOrderByCreatedAt(groupAlbum, true);
        List<User> users = userGroupAlbums.stream().map(UserGroupAlbum::getUser).collect(Collectors.toList());

        List<Long> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        if (!userIds.contains(groupAlbum.getHostUserId())) {
            throw new BadRequestException(NOT_EXIST_HOST);
        }
        return users;
    }

    /**
     * 현재 로그인한 사용자의 단체앨범 목록 조회
     * @param user 현재 로그인한 사용자 Entity
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
     * @param user 현재 로그인한 사용자 Entity
     * @param groupAlbum 단체앨범 Entity
     * @return UserGroupAlbum 없으면 null
     */
    @Transactional(readOnly = true)
    public UserGroupAlbum getUserGroupAlbum(User user, GroupAlbum groupAlbum) {
        return userGroupAlbumRepository.findByUserAndGroupAlbumAndIsActive(user, groupAlbum, true)
                .orElse(null);
    }

    /**
     * 멤버들을 단체앨범에 등록
     * @param users 멤버 리스트
     * @param groupAlbum 단체앨범 Entity
     * @return GroupAlbum 단체앨범 Entity
     */
    @Transactional
    public GroupAlbum registerUsers(User user, List<User> users, GroupAlbum groupAlbum) {
        if (groupAlbum.getHostUserId() != user.getId()) {
            throw new BadRequestException(NOT_HOST);
        }

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
     * @param users 멤버 리스트
     * @param groupAlbum 단체앨범 Entity
     * @return GroupAlbum 단체앨범 Entity
     */
    @Transactional
    public GroupAlbum banUsers(User user, List<User> users, GroupAlbum groupAlbum) {
        if (groupAlbum.getHostUserId() != user.getId()) {
            throw new BadRequestException(NOT_HOST);
        }

        List<UserGroupAlbum> userGroupAlbums = new ArrayList<>();

        for (User member : users) {
            userGroupAlbums.add(getUserGroupAlbum(member, groupAlbum));
        }
        userGroupAlbumRepository.deleteAll(userGroupAlbums);
        return groupAlbum;
    }

    /**
     * 현재 단체앨범에서 나가기
     * @param user 현재 로그인한 사용자 Entity
     * @param groupAlbum 단체앨범 Entity
     * @return GroupAlbum
     */
    @Transactional
    public GroupAlbum outGroupAlbum(User user, GroupAlbum groupAlbum) {
        UserGroupAlbum userGroupAlbum = getUserGroupAlbum(user, groupAlbum);

        if (userGroupAlbum == null) {
            throw new BadRequestException(NOT_MEMBER);
        }

        if (groupAlbum.getHostUserId() == user.getId()) {
            List<User> users = getMembers(groupAlbum);

            if (users.size() == 1) {
                throw new BadRequestException(HOST_MUST_STAY);
                //groupAlbum = deleteGroupAlbum(groupAlbum);
            }
            else {
                for (User member : users) {
                    if (member.getId() == groupAlbum.getHostUserId()) {
                        users.remove(member);
                        break;
                    }
                }
                groupAlbum = delegateHost(users, groupAlbum);
            }
        }
        userGroupAlbumRepository.delete(userGroupAlbum);
        return groupAlbum;
    }

    /**
     * 현재 단체앨범 방장 위임하기
     * @param users 단체앨범 멤버 리스트
     * @param groupAlbum 단체앨범 Entity
     * @retun User 새로운 방장 Entity
     */
    @Transactional
    public GroupAlbum delegateHost(List<User> users, GroupAlbum groupAlbum) {
        groupAlbum.setHostUserId(users.get(0).getId());
        return groupAlbumRepository.saveAndFlush(groupAlbum);
    }

    /**
     * 현재 단체앨범 삭제하기
     * @param
     * @return
     */
    @Transactional
    public GroupAlbum deleteGroupAlbum(GroupAlbum groupAlbum) {
        //:TODO 방장만 남아있을 시 모든 데이터 비활성화 후 단체앨범 비활성화
        return groupAlbum;
    }
}
