package soma.everyonepick.api.album.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soma.everyonepick.api.album.component.PickMapper;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.dto.PickRequestDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.service.GroupAlbumService;
import soma.everyonepick.api.album.service.PhotoService;
import soma.everyonepick.api.album.service.PickPhotoService;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 사진선택 관련 Endpoint
 */
@Tag(description = "사진선택 관련 Endpoint", name = "사진선택")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album/{groupAlbumId}/pick")
public class PickController {

    private final PickService pickService;
    private final PhotoService photoService;
    private final PickPhotoService pickPhotoService;
    private final GroupAlbumService groupAlbumService;
    private final PickMapper pickMapper;

    @Operation(description = "단체앨범 사진선택 작업 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "")
    public ResponseEntity<ApiResult<List<PickDto.PickListDto>>> getPicks(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId
    ) {
        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        pickService.getPicksByGroupAlbum(groupAlbum).stream()
                                .map(pickMapper::toListDto)
                                .collect(Collectors.toList())
                )
        );
    }

    @Operation(description = "단체앨범 사진선택 작업 상세 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "/{pickId}")
    public ResponseEntity<ApiResult<PickDto.PickDetailDto>> getPick(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "사진선택 작업 id", required = true)
            @PathVariable Long pickId
    ) {
        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        pickMapper.toDetailDto(
                                pickService.getPickById(pickId)
                        )
                )
        );
    }

    @Operation(description = "단체앨범 사진선택 작업 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping(value = "")
    public ResponseEntity<ApiResult<PickDto.PickDetailDto>> postPick(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "사진선택 작업에 포함될 사진들")
            @RequestBody @Valid PickRequestDto pickRequestDto
    ) {
        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);
        Pick pick = pickService.createPick(groupAlbum, pickRequestDto);

        List<Photo> photos = pickRequestDto.getPhotos().stream()
                .map(s -> photoService.getPhotosById(s.getId()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ApiResult.ok(
                        pickMapper.toDetailDto(
                                pickPhotoService.registerPhotos(groupAlbum, pick, photos)
                        )
                )
        );
    }
}
