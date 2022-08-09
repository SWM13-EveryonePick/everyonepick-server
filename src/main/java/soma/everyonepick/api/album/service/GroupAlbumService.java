package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.repository.GroupAlbumRepository;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_GROUP_ALBUM;

@Validated
@Service
@RequiredArgsConstructor
public class GroupAlbumService {
    private final GroupAlbumRepository groupAlbumRepository;

    /**
     * 단체앨범 상세 조회
     * @param groupAlbumId 조회하고자 하는 단체앨범 id
     * @return 단체앨범 엔티티
     */
    @Transactional
    public GroupAlbum getGroupAlbumById(Long groupAlbumId) {
        return groupAlbumRepository.findByIdAndIsActive(groupAlbumId, true)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_GROUP_ALBUM));
    }
}
