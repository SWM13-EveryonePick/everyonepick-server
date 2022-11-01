package soma.everyonepick.api.album.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.album.component.PickMapper;
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.dto.PickRequestDto;
import soma.everyonepick.api.album.entity.GroupAlbum;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.service.*;
import soma.everyonepick.api.album.validator.PickPhotoValidator;
import soma.everyonepick.api.album.validator.PickValidator;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.core.exception.BadRequestException;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static soma.everyonepick.api.core.message.ErrorMessage.WRONG_FACE_NUM;

/**
 * 사진선택 관련 Endpoint
 */
@Tag(description = "사진선택 관련 Endpoint", name = "사진선택")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums/{groupAlbumId}/picks")
public class PickController {

    private final PickService pickService;
    private final PhotoService photoService;
    private final PickPhotoService pickPhotoService;
    private final GroupAlbumService groupAlbumService;
    private final PickInfoService pickInfoService;
    private final PickMapper pickMapper;
    private final PickValidator pickValidator;
    private final PickPhotoValidator pickPhotoValidator;

    @Operation(description = "단체앨범 사진선택 작업 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "")
    public ResponseEntity<ApiResult<List<PickDto.PickListDto>>> getPicks(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId
    ) {
        GroupAlbum groupAlbum = groupAlbumService.getGroupAlbumById(groupAlbumId);
        List<Pick> picks = pickService.getPicksByGroupAlbum(groupAlbum).stream()
                .filter(pickValidator::pickValidator)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                ApiResult.ok(
                        setIsDoneFlags(user, picks)
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

        List<Photo> photos = pickRequestDto.getPhotos().stream()
                .map(s -> photoService.getPhotosById(s.getId()))
                .collect(Collectors.toList());

        if (!pickPhotoValidator.pickPhotoValidator(groupAlbum, photos)) {
            throw new BadRequestException(WRONG_FACE_NUM);
        }

        Pick pick = pickService.createPick(groupAlbum);

        return ResponseEntity.ok(
                ApiResult.ok(
                        pickMapper.toDetailDto(
                                pickPhotoService.registerPhotos(groupAlbum, pick, photos)
                        )
                )
        );
    }

    private List<PickDto.PickListDto> setIsDoneFlags(User user, List<Pick> picks) {
        List<PickDto.PickListDto> pickListDtos = new ArrayList<>();

        for (Pick pick : picks) {
            List<Long> userIds = pickInfoService.getPickInfoThrows(pick).getUserIds();
            PickDto.PickListDto pickListDto = pickMapper.toListDto(pick);
            pickListDto.setIsDone(false);

            if (userIds != null) {
                pickListDto.setIsDone(
                        userIds.stream()
                                .anyMatch(s -> s.equals(user.getId()))
                );
            }
            pickListDtos.add(pickListDto);
        }
        return pickListDtos;
    }
}
