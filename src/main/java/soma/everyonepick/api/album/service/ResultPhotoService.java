package soma.everyonepick.api.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.ResultPhoto;
import soma.everyonepick.api.album.repository.ResultPhotoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultPhotoService {
    private final ResultPhotoRepository resultPhotoRepository;

    /**
     * 단체앨범의 모든 합성완료사진 목록 조회
     * @param groupAlbum 단체앨범 엔티티
     * @return List<ResultPhoto>
     */
    @Transactional(readOnly = true)
    public List<ResultPhoto> getResultPhotosByGroupAlbum(GroupAlbum groupAlbum) {
        return resultPhotoRepository.findAllByGroupAlbumAndIsActiveOrderByCreatedAtDesc(groupAlbum, true);
    }
}
