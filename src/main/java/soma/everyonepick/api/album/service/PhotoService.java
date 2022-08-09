package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.repository.PhotoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;

    /**
     * 단체앨범의 사진 목록 조회
     * @param groupAlbum 단체앨범 엔티티
     * @return List<Photo>
     */
    public List<Photo> getPhotos(GroupAlbum groupAlbum) {
        return photoRepository.findAllByGroupAlbumAndIsActive(groupAlbum, true);
    }
}
