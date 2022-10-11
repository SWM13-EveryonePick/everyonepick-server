package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.ResultPhoto;

import java.util.List;

public interface ResultPhotoRepository extends JpaRepository<ResultPhoto, Long> {
    /**
     * 사진선택 작업 엔티티와 활성화 여부로 합성완료 사진 목록 조회
     * @param pick
     * @param isActive
     * @return List<ResultPhoto>
     */
    List<ResultPhoto> findAllByPickAndIsActive(Pick pick, boolean isActive);
}
