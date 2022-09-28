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
import soma.everyonepick.api.album.component.PhotoMapper;
import soma.everyonepick.api.album.dto.PhotoDto;
import soma.everyonepick.api.album.dto.PhotoRequestDto;
import soma.everyonepick.api.album.entity.Photo;
import soma.everyonepick.api.album.service.GroupAlbumService;
import soma.everyonepick.api.album.service.PhotoService;
import soma.everyonepick.api.album.service.PhotoUploadService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 단체앨범 사진 관련 Endpoint
 */
@Tag(description = "단체앨범 사진 관련 Endpoint", name = "단체앨범 사진")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album/{groupAlbumId}/photo")
public class PhotoController {
    private final GroupAlbumService groupAlbumService;
    private final PhotoService photoService;
    private final PhotoUploadService photoUploadService;
    private final PhotoMapper photoMapper;


    @Operation(description = "단체앨범 사진조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<PhotoDto.PhotoResponseDto>>> getPhotos(
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

    @Operation(description = "단체앨범 사진등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping(
            value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResult<List<PhotoDto.PhotoResponseDto>>> postPhotos(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "업로드할 이미지 리스트")
            @RequestPart("images") List<MultipartFile> images
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        photoUploadService.uploadPhotos(
                                        user,
                                        images,
                                        groupAlbumService.getGroupAlbumById(groupAlbumId)
                                ).stream()
                                .map(photoMapper::toDto)
                                .collect(Collectors.toList())
                )
        );
    }

    @Operation(description = "단체앨범 사진삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    @DeleteMapping(value = "")
    public ResponseEntity<ApiResult<List<PhotoDto>>> deletePhotos(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "단체앨범 사진 요청 모델")
            @RequestBody @Valid PhotoRequestDto photoRequestDto
    ) {
        List<Photo> photos = new ArrayList<>();

        for (PhotoDto photoDto: photoRequestDto.getPhotos()) {
            photos.add(photoService.getPhotosById(photoDto.getId()));
        }

        return ResponseEntity.ok(
                ApiResult.ok(
                        photoService.deletePhotos(
                                        user,
                                        photos,
                                        groupAlbumService.getGroupAlbumById(groupAlbumId)
                                ).stream()
                                .map(photoMapper::toDto)
                                .collect(Collectors.toList())
                )
        );
    }
}
