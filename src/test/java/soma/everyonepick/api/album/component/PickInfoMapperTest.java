package soma.everyonepick.api.album.component;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import soma.everyonepick.api.album.dto.PickInfoResponseDto;
import soma.everyonepick.api.album.entity.PickInfoUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PickInfoMapperTest {

    @Autowired
    private PickInfoMapper pickInfoMapper;

    //@Test
    @DisplayName("toDto_변환_테스트")
    void toDto() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);

        PickInfoUser pickInfoUser = PickInfoUser.builder()
                .pickId("1")
                .timeOut(36000L)
                .userIds(userIds)
                .build();
        PickInfoResponseDto responseDto = pickInfoMapper.toDto(pickInfoUser);

        assertEquals(2L, responseDto.getPickUserCnt());
        assertEquals(2L, responseDto.getUserCnt());
    }

    //@Test
    @DisplayName("toDto_변환_테스트 - userIds 요소가 없을 때")
    void toDto_userIds_null() {

        List<Long> userIds = new ArrayList<>();

        PickInfoUser pickInfoUser = PickInfoUser.builder()
                .pickId("1")
                .timeOut(3600L)
                .userIds(userIds)
                .build();

        PickInfoResponseDto responseDto = pickInfoMapper.toDto(pickInfoUser);

        assertEquals(0L, responseDto.getPickUserCnt());
        assertEquals(2L, responseDto.getUserCnt());
    }


}
