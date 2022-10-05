package soma.everyonepick.api.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.repository.PickInfoUserRepository;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;

import java.util.List;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_PICK;

@Service
@RequiredArgsConstructor
public class PickInfoService {
    private final PickInfoUserRepository pickInfoUserRepository;

    /**
     * 단체앨범의 사진선택 정보 조회
     *
     * @param pick 사진선택 작업 엔티티
     * @return PickInfoUser 사진선택 정보
     */
    public PickInfoUser getPickInfoByPick(Pick pick) {
        return pickInfoUserRepository.findById(pick.getId().toString())
                .orElseThrow(()->new ResourceNotFoundException(NOT_EXIST_PICK));
    }
}
