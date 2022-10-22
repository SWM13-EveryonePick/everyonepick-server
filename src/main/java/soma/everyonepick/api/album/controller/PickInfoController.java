package soma.everyonepick.api.album.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.album.component.PickInfoMapper;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.dto.PhotoRequestDto;
import soma.everyonepick.api.album.dto.PickInfoResponseDto;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.entity.Pick;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.service.PhotoService;
import soma.everyonepick.api.album.service.PickInfoService;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 사진선택 정보 관련 Endpoint
 */
@Tag(description = "사진선택 정보 관련 Endpoint", name = "사진선택 정보")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/picks/{pickId}/pick-info")
public class PickInfoController {
    private final PickInfoMapper pickInfoMapper;
    private final PickInfoService pickInfoService;
    private final PickService pickService;
    private final PhotoService photoService;

    @Operation(description = "단체앨범 사진선택 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "")
    public ResponseEntity<ApiResult<PickInfoResponseDto>> getPickInfo(
            @Parameter(description = "사진선택 작업 id", required = true)
            @PathVariable Long pickId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        pickInfoMapper.toDto(
                                pickInfoService.getPickInfoByPick(
                                        pickService.getPickById(pickId)
                                )
                        )
                )
        );
    }

    @Operation(description = "단체앨범 사진선택 정보 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping(value = "")
    public ResponseEntity<ApiResult<PickInfoResponseDto>> postPickInfo(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "사진선택 작업 id", required = true)
            @PathVariable Long pickId,
            @Parameter(description = "단체앨범 사진 요청 모델")
            @RequestBody @Valid PhotoRequestDto photoRequestDto
    ) {
        List<PhotoDto> photoDtos = photoRequestDto.getPhotos();
        List<Photo> photos = new ArrayList<>();

        if (photoDtos.size() != 0) {
            for (PhotoDto photoDto: photoDtos) {
                photos.add(photoService.getPhotosById(photoDto.getId()));
            }
        }

        Pick pick = pickService.getPickById(pickId);

        return ResponseEntity.ok(
                ApiResult.ok(
                        pickInfoMapper.toDto(
                                pickInfoService.createPickInfo(user, pick, photos)
                        )
                )
        );
    }
}
