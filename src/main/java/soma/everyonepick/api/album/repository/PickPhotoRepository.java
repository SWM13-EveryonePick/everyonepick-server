package soma.everyonepick.api.album.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickPhoto;

import java.util.List;

public interface PickPhotoRepository extends JpaRepository<PickPhoto, Long> {

    /**
     * 사진선택 작업 엔티티와 활성화 여부로 PickPhoto 목록 조회
     * @param pick
     * @param isActive
     * @return List<PickPhoto>
     */
    List<PickPhoto> findAllByPickAndIsActive(Pick pick, boolean isActive);
}
