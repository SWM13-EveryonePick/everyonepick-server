package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.repository.PickRepository;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;

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
     * @return Pick 사진선택 작업 엔티티
     */
    @Transactional
    public Pick createPick(GroupAlbum groupAlbum) {
        Pick pick = Pick
                .builder()
                .groupAlbum(groupAlbum)
                .build();
        pick = pickRepository.saveAndFlush(pick);

        return pick;
    }

    /**
     * 단체앨범의 사진선택 작업 삭제
     *
     * @param pick 사진선택 작업 엔티티
     * @return Pick 사진선택 작업 엔티티
     */
    @Transactional
    public Pick deletePick(Pick pick) {
        pick.setIsActive(false);
        return pickRepository.saveAndFlush(pick);
    }
}
