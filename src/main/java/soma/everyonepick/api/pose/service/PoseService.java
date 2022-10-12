package soma.everyonepick.api.pose.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.pose.entity.Pose;
import soma.everyonepick.api.pose.repository.PoseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PoseService {
    private final PoseRepository poseRepository;

    /**
     * 인원수 별 단체포즈 목록 조회
     * @param peopleNum 인원수
     * @return List<Pose>
     */
    @Transactional(readOnly = true)
    public List<Pose> getPosesByPeopleNum(String peopleNum) {
        return poseRepository.findAllByPeopleNumAndIsActive(peopleNum, true);
    }
}
