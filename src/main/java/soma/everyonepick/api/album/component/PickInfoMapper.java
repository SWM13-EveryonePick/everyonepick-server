package soma.everyonepick.api.album.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.PickInfoResponseDto;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.user.entity.User;

import java.util.List;

import static java.lang.Long.parseLong;

@Component
@RequiredArgsConstructor
public class PickInfoMapper {
    private final PickService pickService;
    private final UserGroupAlbumService userGroupAlbumService;

    public PickInfoResponseDto toDto(PickInfoUser pickInfoUser) {
        List<Long> userIds = pickInfoUser.getUserIds();
        Pick pick = pickService.getPickById(parseLong(pickInfoUser.getPickId()));

        List<User> users = userGroupAlbumService.getMembers(pick.getGroupAlbum());
        Long timeOut = pickInfoUser.getTimeOut();

        return PickInfoResponseDto
                .builder()
                .pickUserCnt(userIds.stream().count())
                .userCnt(users.stream().count())
                .timeOut(timeOut)
                .build();
    }
}
