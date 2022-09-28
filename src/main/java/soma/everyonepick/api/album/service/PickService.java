package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.dto.PickRequestDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.repository.PickRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PickService {
    private final PickRepository pickRepository;

    /**
     * 단체앨범의 사진선택 작업 등록
     * @param groupAlbum 단체앨범 엔티티
     * @param pickRequestDto 사진선택 작업 요청 모델
     * @return Pick 사진선택 작업 엔티티
     */
    @Transactional
    public Pick createPick(GroupAlbum groupAlbum, PickRequestDto pickRequestDto) {
        Long timeOut = pickRequestDto.getTimeOut();
        LocalDateTime expired_at = LocalDateTime.now().plusMinutes(timeOut);
        Pick pick = Pick
                .builder()
                .expired_at(expired_at)
                .groupAlbum(groupAlbum)
                .build();

        return pickRepository.saveAndFlush(pick);
    }
}
