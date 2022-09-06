package soma.everyonepick.api.album.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import soma.everyonepick.api.album.dto.GroupAlbumRequestDto;
import soma.everyonepick.api.album.dto.GroupAlbumUpdateDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.repository.GroupAlbumRepository;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.core.exception.UnauthorizedException;
import soma.everyonepick.api.user.entity.User;

import static soma.everyonepick.api.core.message.ErrorMessage.*;

@Validated
@Service
@RequiredArgsConstructor
public class GroupAlbumService {
    private final GroupAlbumRepository groupAlbumRepository;

    /**
     * 단체앨범 id로 조회
     *
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
     *
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
     *
     * @param groupAlbumRequestDto 단체앨범 요청 Dto
     * @param user          방장 사용자 엔티티
     * @return 단체앨범 엔티티
     */
    @Transactional
    public GroupAlbum createGroupAlbum(User user, GroupAlbumRequestDto groupAlbumRequestDto) {
        if (getGroupAlbumByTitle(groupAlbumRequestDto.getTitle()) != null) {
            throw new BadRequestException(REDUNDANT_TITLE);
        }
        GroupAlbum groupAlbum = GroupAlbum.builder()
                .title(groupAlbumRequestDto.getTitle())
                .hostUserId(user.getId())
                .build();
        return groupAlbumRepository.saveAndFlush(groupAlbum);
    }

    /**
     * 단체앨범 수정
     *
     * @param groupAlbumUpdateDto 단체앨범 수정 Dto
     * @param user 방장 사용자 엔티티
     * @param groupAlbumId 단체앨범 id
     * @return 단체앨범 엔티티
     */
    @Transactional
    public GroupAlbum updateGroupAlbum(User user, GroupAlbumUpdateDto groupAlbumUpdateDto, Long groupAlbumId) {
        GroupAlbum groupAlbum = getGroupAlbumById(groupAlbumId);
        if (user.getId() != groupAlbum.getHostUserId()) {
            throw new UnauthorizedException(NOT_HOST);
        }

        if (getGroupAlbumByTitle(groupAlbumUpdateDto.getTitle()) != null) {
            throw new BadRequestException(REDUNDANT_TITLE);
        }

        groupAlbum.setTitle(groupAlbumUpdateDto.getTitle());
        return groupAlbumRepository.saveAndFlush(groupAlbum);
    }
}
