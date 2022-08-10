package soma.everyonepick.api.album.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import soma.everyonepick.api.album.dto.GroupAlbumCreateDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.repository.GroupAlbumRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.user.entity.User;

import static soma.everyonepick.api.core.message.ErrorMessage.NOT_EXIST_GROUP_ALBUM;
import static soma.everyonepick.api.core.message.ErrorMessage.REDUNDANT_TITLE;

@Validated
@Service
@RequiredArgsConstructor
public class GroupAlbumService {
    private final GroupAlbumRepository groupAlbumRepository;

    /**
     * 단체앨범 id로 조회
     * @param groupAlbumId 조회하고자 하는 단체앨범 id
     * @return 단체앨범 엔티티
     */
    @Transactional(readOnly = true)
    public GroupAlbum getGroupAlbumById(Long groupAlbumId) {
        return groupAlbumRepository.findByIdAndIsActive(groupAlbumId, true)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_GROUP_ALBUM));
    }

    /**
     * 단체앨범 제목으로 조회
     * @param title 조회하고자 하는 단체앨범 제목
     * @return 단체앨범 엔티티
     */
    @Transactional(readOnly = true)
    public GroupAlbum getGroupAlbumByTitle(String title) {
        return groupAlbumRepository.findByTitleAndIsActive(title, true)
                .orElse(null);
    }

    /**
     * 단체앨범 생성
     * @param groupAlbumCreateDto 단체앨범 생성 Dto
     * @param user 방장 사용자 엔티티
     * @return 단체앨범 엔티티
     */
    @Transactional
    public GroupAlbum createGroupAlbum(GroupAlbumCreateDto groupAlbumCreateDto, User user) {
        if (getGroupAlbumByTitle(groupAlbumCreateDto.getTitle()) != null) {
            throw new BadRequestException(REDUNDANT_TITLE);
        }
        GroupAlbum groupAlbum = GroupAlbum.builder()
                .title(groupAlbumCreateDto.getTitle())
                .hostUserId(user.getId())
                .build();
        return groupAlbumRepository.saveAndFlush(groupAlbum);
    }
}
