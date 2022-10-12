package soma.everyonepick.api.pose.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soma.everyonepick.api.pose.entity.Pose;

import java.util.List;

public interface PoseRepository extends JpaRepository<Pose, Long> {
    /**
     * 인원수와 활성화 여부로 합성완료 사진 목록 조회
     * @param peopleNum
     * @param isActive
     * @return List<Pose>
     */
    List<Pose> findAllByPeopleNumAndIsActive(String peopleNum, boolean isActive);
}
