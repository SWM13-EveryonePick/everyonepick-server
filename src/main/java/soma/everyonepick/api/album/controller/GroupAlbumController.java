package soma.everyonepick.api.album.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soma.everyonepick.api.album.component.GroupAlbumMapper;
import soma.everyonepick.api.album.dto.GroupAlbumReadDetailDto;
import soma.everyonepick.api.album.dto.GroupAlbumReadListDto;
import soma.everyonepick.api.album.service.GroupAlbumService;
import soma.everyonepick.api.album.service.UserGroupAlbumService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.entity.User;

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

    @Operation(description = "단체앨범 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<GroupAlbumReadListDto>>> getGroupAlbums(
            @Parameter(hidden = true)
            @CurrentUser User loggedInUser
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userGroupAlbumService.getMyGroupAlbums(loggedInUser).stream()
                                .map(s -> groupAlbumMapper.toReadListDto(s))
                                .collect(Collectors.toList())
                ));
    }
}
