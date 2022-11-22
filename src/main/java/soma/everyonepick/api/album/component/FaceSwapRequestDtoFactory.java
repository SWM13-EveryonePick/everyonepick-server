package soma.everyonepick.api.album.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.album.dto.FaceSwapRequestDto;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.dto.PickInfoPhotoDto;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoPhoto;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.entity.PickPhoto;
import soma.everyonepick.api.album.repository.PickInfoPhotoRepository;
import soma.everyonepick.api.album.repository.PickInfoUserRepository;
import soma.everyonepick.api.album.repository.PickPhotoRepository;
import soma.everyonepick.api.album.repository.PickRepository;
import soma.everyonepick.api.album.service.PickPhotoService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FaceSwapRequestDtoFactory {

    private final PickMapper pickMapper;
    private final UserGroupAlbumService userGroupAlbumService;
    private final PickPhotoService pickPhotoService;
    private final PickInfoPhotoRepository pickInfoPhotoRepository;
    private final PickInfoUserRepository pickInfoUserRepository;
    private final PickRepository pickRepository;
    private final PickPhotoRepository pickPhotoRepository;

    /**
     * AI서버로 보낼 합성요청 데이터를 구성하고 Redis와 MySQL에 저장된 관련 데이터들을 삭제.
     * @param pick 사진선택 작업 엔티티
     * @return FaceSwapRequestDto 합성요청 데이터
     */
    public FaceSwapRequestDto buildFaceSwapRequestDto(Pick pick) {
        List<PickInfoPhotoDto> pickInfoPhotoDtos = new ArrayList<>();
        List<PickInfoPhoto> pickInfoPhotos = new ArrayList<>();

        List<User> users = userGroupAlbumService.getMembers(pick.getGroupAlbum());

        for (User user: users) {
            String userPickId = user.getId().toString() + "-" + pick.getId().toString();
            PickInfoPhoto pickInfoPhoto = pickInfoPhotoRepository.findById(userPickId)
                    .orElse(new PickInfoPhoto(userPickId, new ArrayList<>()));


            pickInfoPhotoDtos.add(
                    PickInfoPhotoDto.builder()
                    .userId(user.getId())
                    .photoIds(pickInfoPhoto.getPhotoIds())
                    .build()
            );

            if (pickInfoPhoto.getPhotoIds().size() > 0) {
                pickInfoPhotos.add(pickInfoPhoto);
            }
        }
        pickInfoPhotoRepository.deleteAll(pickInfoPhotos);

        Optional<PickInfoUser> pickInfoUser = pickInfoUserRepository.findById(pick.getId().toString());
        pickInfoUser.ifPresent(pickInfoUserRepository::delete);

        pick.setIsActive(false);
        pickRepository.saveAndFlush(pick);

//        List<PickPhoto> pickPhotos = pickPhotoRepository.findAllByPickAndIsActive(pick, true);
//        pickPhotos.forEach(pickPhoto -> pickPhoto.setIsActive(false));
//        pickPhotoRepository.saveAllAndFlush(pickPhotos);

        return FaceSwapRequestDto.builder()
                .pick(pickMapper.toDetailDto(pick))
                .pickInfoPhotos(pickInfoPhotoDtos)
                .build();
    }
}
