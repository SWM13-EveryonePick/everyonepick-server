package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.UserGroupAlbum;
import soma.everyonepick.api.album.repository.PhotoRepository;
import soma.everyonepick.api.core.component.FileNameGenerator;
import soma.everyonepick.api.core.component.FileUploader;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.user.entity.User;

import java.util.ArrayList;
import java.util.List;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_MEMBER;

@Service
@RequiredArgsConstructor
public class PhotoUploadService {
    public static final String DASH = "/";

    private final FileNameGenerator fileNameGenerator;
    private final FileUploader fileUploader;
    private final PhotoRepository photoRepository;
    private final UserGroupAlbumService userGroupAlbumService;

    /**
     * Multipart File 들을 업로드하고, Photo들을 생성한다.
     * @param user 로그인한 사용자
     * @param imageFiles 요청으로 들어온 Multipart File List
     * @param groupAlbum 단체앨범 엔티티
     * @return 저장 결과 Photo 리스트
     */
    @Transactional
    public List<Photo> uploadPhotos(User user, List<MultipartFile> imageFiles, GroupAlbum groupAlbum) {
        UserGroupAlbum userGroupAlbum = userGroupAlbumService.getUserGroupAlbum(user, groupAlbum);

        if (userGroupAlbum == null) {
            throw new BadRequestException(NOT_MEMBER);
        }

        final String ALBUM_ID = groupAlbum.getTitle() + '_' + groupAlbum.getId().toString();

        List<Photo> photos = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            String generatedFileName = fileNameGenerator.generate(imageFile.getOriginalFilename());
            generatedFileName = ALBUM_ID + DASH + generatedFileName;
            String downloadableUrl = fileUploader.uploadMultiPartFile(imageFile, generatedFileName);

            photos.add(Photo.builder()
                    .photoUrl(downloadableUrl)
                    .groupAlbum(groupAlbum)
                    .build());
        }
        photos = photoRepository.saveAllAndFlush(photos);

        return photos;
    }
}
