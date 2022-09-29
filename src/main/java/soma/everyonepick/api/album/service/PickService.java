package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.dto.PickRequestDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.repository.PickRepository;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_PICK;

@Service
@RequiredArgsConstructor
public class PickService {
    private final PickRepository pickRepository;

    /**
     * 단체앨범의 사진선택 작업 목록 조회
     *
     * @param groupAlbum 단체앨범 엔티티
     * @return List<Pick> 사진선택 작업 리스트
     */
    @Transactional(readOnly = true)
    public List<Pick> getPicksByGroupAlbum(GroupAlbum groupAlbum) {
        return pickRepository.findAllByGroupAlbumAndIsActive(groupAlbum, true);
    }

    /**
     * 단체앨범의 사진선택 작업 상세 조회
     *
     * @param pickId 사진선택 작업 아이디
     * @return Pick 사진선택 작업
     * @throws ResourceNotFoundException
     */
    @Transactional(readOnly = true)
    public Pick getPickById(Long pickId) {
        return pickRepository.findByIdAndIsActive(pickId, true)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_PICK));
    }

    /**
     * 단체앨범의 사진선택 작업 등록
     *
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
