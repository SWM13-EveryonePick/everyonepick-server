package soma.everyonepick.api.album.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soma.everyonepick.api.album.dto.PickInfoUserDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.repository.PickRepository;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PickInfoMapperTest {

    @Mock
    private PickRepository pickRepository;

    @Mock
    private UserGroupAlbumService userGroupAlbumService;

    @InjectMocks
    private PickInfoMapper pickInfoMapper;

    private PickInfoUser pickInfoUser;
    private Pick pick;
    private GroupAlbum groupAlbum;
    private List<User> users;

    @BeforeEach
    public void setUp() {
        pickInfoUser = new PickInfoUser();
        pickInfoUser.setPickId("1");
        pickInfoUser.setUserIds(Arrays.asList(1L, 2L));
        pickInfoUser.setTimeOut(1000L);

        groupAlbum = new GroupAlbum();
        pick = new Pick();
        pick.setId(1L);
        pick.setGroupAlbum(groupAlbum);
    }

    @Test
    @DisplayName("toDto_변환_테스트")
    public void testToDto() {
        users = Arrays.asList(new User(), new User(), new User());
        when(pickRepository.findById(pick.getId())).thenReturn(java.util.Optional.of(pick));
        when(userGroupAlbumService.getMembers(pick.getGroupAlbum())).thenReturn(users);

        PickInfoUserDto.PickInfoUserResponseDto responseDto = pickInfoMapper.toDto(pickInfoUser);

        assertEquals(pickInfoUser.getUserIds().size(), responseDto.getPickUserCnt());
        assertEquals(users.size(), responseDto.getUserCnt());
        assertEquals(pickInfoUser.getTimeOut(), responseDto.getTimeOut());

        verify(pickRepository).findById(pick.getId());
        verify(userGroupAlbumService).getMembers(pick.getGroupAlbum());
    }

    @Test
    @DisplayName("toDto_변환_테스트 - userIds 요소가 없을 때")
    public void testToDtoWithNoUsers() {
        users = new ArrayList<>();
        when(pickRepository.findById(pick.getId())).thenReturn(java.util.Optional.of(pick));
        when(userGroupAlbumService.getMembers(pick.getGroupAlbum())).thenReturn(users);

        PickInfoUserDto.PickInfoUserResponseDto responseDto = pickInfoMapper.toDto(pickInfoUser);

        assertEquals(pickInfoUser.getUserIds().size(), responseDto.getPickUserCnt());
        assertEquals(users.size(), responseDto.getUserCnt());
        assertEquals(pickInfoUser.getTimeOut(), responseDto.getTimeOut());

        verify(pickRepository).findById(pick.getId());
        verify(userGroupAlbumService).getMembers(pick.getGroupAlbum());
    }
}

