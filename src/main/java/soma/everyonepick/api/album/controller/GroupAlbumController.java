package soma.everyonepick.api.album.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.album.component.GroupAlbumMapper;
import soma.everyonepick.api.album.dto.GroupAlbumDto;
import soma.everyonepick.api.album.dto.GroupAlbumReadDetailDto;
import soma.everyonepick.api.album.dto.GroupAlbumReadListDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.service.GroupAlbumService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.dto.UserDto;
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

    @Operation(description = "단체앨범 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<GroupAlbumReadListDto>>> getGroupAlbums(
            @Parameter(hidden = true)
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userGroupAlbumService.getMyGroupAlbums(user).stream()
                                .map(s -> groupAlbumMapper.toReadListDto(s))
                                .collect(Collectors.toList())
                ));
    }

    @Operation(description = "단체앨범 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/{groupAlbumId}")
    public ResponseEntity<ApiResult<GroupAlbumReadDetailDto>> getGroupAlbum(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toReadDetailDto(
                                groupAlbumService.getGroupAlbumById(groupAlbumId))
        ));
    }

    @Operation(description = "단체앨범 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping("")
    public ResponseEntity<ApiResult<GroupAlbumReadDetailDto>> postGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 생성 모델", required = true)
            @RequestBody @Valid GroupAlbumDto groupAlbumDto
            ) {
        List<String> clientIds = groupAlbumDto.getUsers().stream()
                .map(UserDto::getClientId)
                .collect(Collectors.toList());

        GroupAlbum groupAlbum = groupAlbumService.createGroupAlbum(user, groupAlbumDto);
        userGroupAlbumService.registerUsers(user, clientIds, groupAlbum);
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toReadDetailDto(groupAlbum)
                )
        );
    }

    @Operation(description = "단체앨범 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공")
    })
    @PatchMapping("/{groupAlbumId}")
    public ResponseEntity<ApiResult<GroupAlbumReadDetailDto>> patchGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 모델", required = true)
            @RequestBody @Valid GroupAlbumDto groupAlbumDto,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        GroupAlbum groupAlbum = groupAlbumService.updateGroupAlbum(user, groupAlbumDto, groupAlbumId);
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toReadDetailDto(groupAlbum)
                )
        );
    }

    @Operation(description = "단체앨범 나가기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @DeleteMapping("/{groupAlbumId}")
    public ResponseEntity<ApiResult<GroupAlbumReadDetailDto>> outGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);
        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toReadDetailDto(
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
    public ResponseEntity<ApiResult<GroupAlbumReadDetailDto>> inviteUserToGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 모델", required = true)
            @RequestBody @Valid GroupAlbumDto groupAlbumDto,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        List<String> clientIds = groupAlbumDto.getUsers().stream()
                .map(UserDto::getClientId)
                .collect(Collectors.toList());

        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toReadDetailDto(
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
    public ResponseEntity<ApiResult<GroupAlbumReadDetailDto>> banUserFromGroupAlbum(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 모델", required = true)
            @RequestBody @Valid GroupAlbumDto groupAlbumDto,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId

    ) {
        List<String> clientIds = groupAlbumDto.getUsers().stream()
                .map(UserDto::getClientId)
                .collect(Collectors.toList());

        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        groupAlbumMapper.toReadDetailDto(
                                userGroupAlbumService.banUsers(user, clientIds, groupAlbum)
                        )
                )
        );
    }
}
