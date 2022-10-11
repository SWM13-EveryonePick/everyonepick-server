package soma.everyonepick.api.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.ResultPhoto;
import soma.everyonepick.api.album.repository.ResultPhotoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultPhotoService {
    private final ResultPhotoRepository resultPhotoRepository;

    /**
     * 합성완료사진 목록 조회
     * @param pick 사진선택 작업 엔티티
     * @return List<ResultPhoto>
     */
    @Transactional(readOnly = true)
    public List<ResultPhoto> getResultPhotosByPick(Pick pick) {
        return resultPhotoRepository.findAllByPickAndIsActive(pick, true);
    }
}
