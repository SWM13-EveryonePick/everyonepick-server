package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.album.repository.PhotoRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_PHOTO;
import static soma.everyonepick.api.core.message.ErrorMessage.NOT_MEMBER;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final UserGroupAlbumService userGroupAlbumService;

    /**
     * 단체앨범의 사진 목록 조회
     * @param groupAlbum 단체앨범 엔티티
     * @return List<Photo>
     */
    @Transactional(readOnly = true)
    public List<Photo> getPhotosByGroupAlbum(GroupAlbum groupAlbum) {
        return photoRepository.findAllByGroupAlbumAndIsActive(groupAlbum, true);
    }

    /**
     * 단체앨범의 사진 조회
     * @param photoId 사진 id
     * @return Photo
     * @throws ResourceNotFoundException 해당 id가 존재하지 않을 경우
     */
    @Transactional(readOnly = true)
    public Photo getPhotosById(Long photoId) {
        return photoRepository.findByIdAndIsActive(photoId, true)
                .orElseThrow(()-> new ResourceNotFoundException(NOT_EXIST_PHOTO));
    }

    /**
     * 단체앨범의 사진 삭제
     * @param user 현재 로그인한 유저
     * @param groupAlbum 단체앨범 엔티티
     * @return List<Photo>
     */
    @Transactional
    public List<Photo> deletePhotos(User user, List<Photo> photos, GroupAlbum groupAlbum) {
        UserGroupAlbum userGroupAlbum = userGroupAlbumService.getUserGroupAlbum(user, groupAlbum);

        if (userGroupAlbum == null) {
            throw new BadRequestException(NOT_MEMBER);
        }

        for (Photo photo : photos) {
            photo.setIsActive(false);
        }

        return photoRepository.saveAllAndFlush(photos);
    }
}
