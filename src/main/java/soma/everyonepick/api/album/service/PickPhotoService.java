package soma.everyonepick.api.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickPhoto;
import soma.everyonepick.api.album.repository.PickPhotoRepository;
import soma.everyonepick.api.core.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.WRONG_PHOTO;

@Service
@RequiredArgsConstructor
public class PickPhotoService {

    private final PickPhotoRepository pickPhotoRepository;

    /**
     * 단체앨범 사진선택 작업에 포함된 사진 리스트 조회
     * @param pick 사진선택 작업 엔티티
     * @return List<Photo> 사진 리스트
     */
    @Transactional(readOnly = true)
    public List<Photo> getPhotos(Pick pick) {
        return pickPhotoRepository.findAllByPickAndIsActive(pick, true)
                .stream()
                .map(PickPhoto::getPhoto)
                .collect(Collectors.toList());
    }

    /**
     * 단체앨범 사진선택 작업에 후보 사진들 등록
     * @param pick 사진선택 작업 엔티티
     * @param photos 사진 리스트
     * @return Pick 사진선택 작업 엔티티
     */
    @Transactional
    public Pick registerTempPhotos(Pick pick, List<Photo> photos) {
        List<PickPhoto> pickPhotos = new ArrayList<>();

        for (Photo photo : photos) {

            PickPhoto pickPhoto = PickPhoto
                    .builder()
                    .isActive(false)
                    .pick(pick)
                    .photo(photo)
                    .build();
            pickPhotos.add(pickPhoto);
        }
        pickPhotoRepository.saveAllAndFlush(pickPhotos);
        return pick;
    }
}
