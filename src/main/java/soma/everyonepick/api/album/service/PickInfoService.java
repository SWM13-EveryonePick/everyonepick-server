package soma.everyonepick.api.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoPhoto;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.repository.PickInfoPhotoRepository;
import soma.everyonepick.api.album.repository.PickInfoUserRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class PickInfoService {
    private final PickInfoUserRepository pickInfoUserRepository;
    private final PickInfoPhotoRepository pickInfoPhotoRepository;
    private final UserGroupAlbumService userGroupAlbumService;

    /**
     * 단체앨범의 사진선택 정보 조회
     *
     * @param pick 사진선택 작업 엔티티
     * @return PickInfoUser 사진선택 현황
     * @throws ResourceNotFoundException
     */
    public PickInfoUser getPickInfoThrows(Pick pick) {
        return pickInfoUserRepository.findById(pick.getId().toString())
                .orElseThrow(()->new ResourceNotFoundException(NOT_EXIST_PICK_INFO));
    }

    /**
     * 단체앨범의 사진선택 정보 조회
     *
     * @param pick 사진선택 작업 엔티티
     * @return Optional<PickInfoUser> 사진선택 현황
     */
    public Optional<PickInfoUser> getPickInfo(Pick pick) {
        return pickInfoUserRepository.findById(pick.getId().toString());
    }

    /**
     * 단체앨범의 사진선택 정보 등록
     *
     * @param user 선택을 한 유저 엔티티
     * @param pick 선택 작업 엔티티
     * @param photos 선택한 사진 리스트
     * @return PickInfoUser 사진선택 현황
     */
    public PickInfoUser createPickInfo(User user, Pick pick, List<Photo> photos) {
        if (photos.size() != 0) {
            String userPickId = user.getId().toString() + "-" + pick.getId().toString();

            List<Long> photoIds = photos.stream()
                    .map(Photo::getId)
                    .collect(Collectors.toList());

            PickInfoPhoto pickInfoPhoto = PickInfoPhoto
                    .builder()
                    .userPickId(userPickId)
                    .photoIds(photoIds)
                    .build();
            pickInfoPhotoRepository.save(pickInfoPhoto);
        }

        PickInfoUser pickInfoUser = pickInfoUserRepository.findById(pick.getId().toString())
                .orElseThrow(() -> new ResourceNotFoundException(TIME_IS_UP));

        List<Long> userIds = pickInfoUser.getUserIds();

        if (userIds == null){
            userIds = new ArrayList<>();
        }

        for (Long userId : userIds) {
            if (userId.equals(user.getId())) {
                throw new BadRequestException(REDUNDANT_SELECT);
            }
        }
        userIds.add(user.getId());
        pickInfoUser.setUserIds(userIds);

        if (userGroupAlbumService.getMembers(pick.getGroupAlbum()).size() == userIds.size()) {
            //:TODO 사진 합성 이벤트 발행
        }

        return pickInfoUserRepository.save(pickInfoUser);
    }

    /**
     * 단체앨범의 사진선택 유저 정보 등록
     *
     * @param pick 사진선택 작업 엔티티
     * @param timeOut 제한시간(분)
     * @return PickInfoUser 사진선택 현황
     */
    public PickInfoUser createPickInfoUser(Pick pick, Long timeOut) {
        PickInfoUser pickInfoUser = PickInfoUser
                .builder()
                .pickId(pick.getId().toString())
                .timeOut(timeOut)
                .build();

        return pickInfoUserRepository.save(pickInfoUser);
    }
}
