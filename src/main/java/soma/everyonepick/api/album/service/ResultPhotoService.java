package soma.everyonepick.api.album.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.ResultPhoto;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.album.repository.ResultPhotoRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.entity.User;

import java.util.List;
import java.util.Optional;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_RESULT_PHOTO;
import static soma.everyonepick.api.core.message.ErrorMessage.NOT_MEMBER;

@Service
@RequiredArgsConstructor
public class ResultPhotoService {
    private final ResultPhotoRepository resultPhotoRepository;
    private final UserGroupAlbumService userGroupAlbumService;

    /**
     * 합성완료사진 상세조회
     * @param resultPhotoId 합성완료사진 id
     * @return Optional<ResultPhoto>
     */
    @Transactional(readOnly = true)
    public ResultPhoto getResultPhoto(Long resultPhotoId) {
        return resultPhotoRepository.findByIdAndIsActive(resultPhotoId, true)
                .orElseThrow(()-> new ResourceNotFoundException(NOT_EXIST_RESULT_PHOTO));
    }

    /**
     * 단체앨범의 모든 합성완료사진 목록 조회
     * @param groupAlbum 단체앨범 엔티티
     * @return List<ResultPhoto>
     */
    @Transactional(readOnly = true)
    public List<ResultPhoto> getResultPhotosByGroupAlbum(GroupAlbum groupAlbum) {
        return resultPhotoRepository.findAllByGroupAlbumAndIsActiveOrderByCreatedAtDesc(groupAlbum, true);
    }

    /**
     * 합성완료사진 삭제
     * @param resultPhotos 합성완료사진 리스트
     * @return List<ResultPhoto>
     */
    @Transactional
    public List<ResultPhoto> deleteResultPhotos(User user, GroupAlbum groupAlbum, List<ResultPhoto> resultPhotos) {
        UserGroupAlbum userGroupAlbum = userGroupAlbumService.getUserGroupAlbum(user, groupAlbum);

        if (userGroupAlbum == null) {
            throw new BadRequestException(NOT_MEMBER);
        }

        for (ResultPhoto resultPhoto : resultPhotos) {
            resultPhoto.setIsActive(false);
        }

        return resultPhotoRepository.saveAllAndFlush(resultPhotos);
    }
}
