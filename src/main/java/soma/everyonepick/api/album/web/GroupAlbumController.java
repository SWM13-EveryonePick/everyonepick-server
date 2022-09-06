package soma.everyonepick.api.album.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.album.component.GroupAlbumMapper;
import soma.everyonepick.api.album.component.PhotoMapper;
import soma.everyonepick.api.album.dto.GroupAlbumRequestDto;
import soma.everyonepick.api.album.dto.GroupAlbumListResponseDto;
import soma.everyonepick.api.album.dto.GroupAlbumResponseDto;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.service.GroupAlbumService;
import soma.everyonepick.api.album.service.PhotoService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.dto.UserRequestDto;
import soma.everyonepick.api.user.dto.UserResponseDto;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 단체앨범 관련 Endpoint
 */
@Tag(description = "단체앨범 관련 Endpoint", name = "단체앨범")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album")
public class GroupAlbumController {
    private final GroupAlbumService groupAlbumService;
    private final GroupAlbumMapper groupAlbumMapper;
    private final UserGroupAlbumService userGroupAlbumService;
    private final PhotoService photoService;
    private final PhotoMapper photoMapper;

    @Operation(description = "단체앨범 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<GroupAlbumListResponseDto>>> getGroupAlbums(
            @Parameter(hidden = true)
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userGroupAlbumService.getMyGroupAlbums(user).stream()
                                .map(groupAlbumMapper::toListResponseDto)
                                .collect(Collectors.toList())
                ));
    }

    @Operation(description = "단체앨범 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/{groupAlbumId}")
    public ResponseEntity<ApiResult<GroupAlbumResponseDto>> getGroupAlbum(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toResponseDto(
                                groupAlbumService.getGroupAlbumById(groupAlbumId))
        ));
    }

    @Operation(description = "단체앨범 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping("")
    public ResponseEntity<ApiResult<GroupAlbumResponseDto>> postGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 생성 모델", required = true)
            @RequestBody @Valid GroupAlbumRequestDto groupAlbumRequestDto
            ) {
        List<String> clientIds = groupAlbumRequestDto.getUsers().stream()
                .map(UserRequestDto::getClientId)
                .collect(Collectors.toList());

        GroupAlbum groupAlbum = groupAlbumService.createGroupAlbum(user, groupAlbumRequestDto);
        userGroupAlbumService.registerUsers(user, clientIds, groupAlbum);
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toResponseDto(groupAlbum)
                )
        );
    }

    @Operation(description = "단체앨범 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PatchMapping("/{groupAlbumId}")
    public ResponseEntity<ApiResult<GroupAlbumResponseDto>> patchGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 모델", required = true)
            @RequestBody @Valid GroupAlbumRequestDto groupAlbumRequestDto,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        GroupAlbum groupAlbum = groupAlbumService.updateGroupAlbum(user, groupAlbumRequestDto, groupAlbumId);
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toResponseDto(groupAlbum)
                )
        );
    }

    @Operation(description = "단체앨범 나가기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @DeleteMapping("/{groupAlbumId}")
    public ResponseEntity<ApiResult<GroupAlbumResponseDto>> outGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toResponseDto(
                                userGroupAlbumService.outGroupAlbum(user, groupAlbum)
                        )
                )
        );
    }

    @Operation(description = "단체앨범에 사용자 초대")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/{groupAlbumId}/user")
    public ResponseEntity<ApiResult<GroupAlbumResponseDto>> inviteUserToGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 모델", required = true)
            @RequestBody @Valid GroupAlbumRequestDto groupAlbumRequestDto,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        List<String> clientIds = groupAlbumRequestDto.getUsers().stream()
                .map(UserRequestDto::getClientId)
                .collect(Collectors.toList());

        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toResponseDto(
                                userGroupAlbumService.registerUsers(user, clientIds, groupAlbum)
                        )
                )
        );
    }

    @Operation(description = "단체앨범에 사용자 강퇴")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PatchMapping("/{groupAlbumId}/user")
    public ResponseEntity<ApiResult<GroupAlbumResponseDto>> banUserFromGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 모델", required = true)
            @RequestBody @Valid GroupAlbumRequestDto groupAlbumRequestDto,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        List<String> clientIds = groupAlbumRequestDto.getUsers().stream()
                .map(UserRequestDto::getClientId)
                .collect(Collectors.toList());

        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toResponseDto(
                                userGroupAlbumService.banUsers(user, clientIds, groupAlbum)
                        )
                )
        );
    }

    @Operation(description = "단체앨범 사진조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/{groupAlbumId}/photo")
    public ResponseEntity<ApiResult<List<PhotoDto>>> getPhotos(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        photoService.getPhotosByGroupAlbum(
                                groupAlbumService.getGroupAlbumById(groupAlbumId)
                        ).stream()
                                .map(photoMapper::toDto)
                                .collect(Collectors.toList())
                        )
                );
    }
}
