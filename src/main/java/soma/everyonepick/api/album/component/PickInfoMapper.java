package soma.everyonepick.api.album.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.PickInfoUserDto;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.repository.PickRepository;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;
import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_PICK;

@Component
@RequiredArgsConstructor
public class PickInfoMapper {
    private final PickService pickService;
    private final PickRepository pickRepository;
    private final UserGroupAlbumService userGroupAlbumService;

    public PickInfoUserDto.PickInfoUserResponseDto toDto(PickInfoUser pickInfoUser) {
        List<Long> userIds = pickInfoUser.getUserIds();

        if (userIds == null) {
            userIds = new ArrayList<>();
        }


        Pick pick = pickRepository.findById(parseLong(pickInfoUser.getPickId()))
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_PICK));
        List<User> users = userGroupAlbumService.getMembers(pick.getGroupAlbum());
        Long timeOut = pickInfoUser.getTimeOut();

        return PickInfoUserDto.PickInfoUserResponseDto
                .builder()
                .pickUserCnt((long) userIds.size())
                .userCnt((long) users.size())
                .timeOut(timeOut)
                .build();
    }
}
