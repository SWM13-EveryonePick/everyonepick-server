package soma.everyonepick.api.album.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.album.component.ResultPhotoMapper;
import soma.everyonepick.api.album.dto.ResultPhotoDto;
import soma.everyonepick.api.album.dto.ResultPhotoRequestDto;
import soma.everyonepick.api.album.entity.ResultPhoto;
import soma.everyonepick.api.album.service.GroupAlbumService;
import soma.everyonepick.api.album.service.ResultPhotoService;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 합성완료 사진 관련 Endpoint
 */
@Tag(description = "합성완료 사진 관련 Endpoint", name = "합성완료 사진")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums/{groupAlbumId}/results")
public class ResultPhotoController {
    private final ResultPhotoService resultPhotoService;
    private final GroupAlbumService groupAlbumService;
    private final ResultPhotoMapper resultPhotoMapper;

    @Operation(description = "합성완료 사진조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<ResultPhotoDto.ResultPhotoResponseDto>>> getResultPhotos(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        resultPhotoService.getResultPhotosByGroupAlbum(
                                groupAlbumService.getGroupAlbumById(groupAlbumId)
                        ).stream()
                                .map(resultPhotoMapper::toResponseDto)
                                .collect(Collectors.toList())
                )
        );
    }

    @Operation(description = "합성완료 사진삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제성공")
    })
    @DeleteMapping("")
    public ResponseEntity<ApiResult<List<ResultPhotoDto.ResultPhotoResponseDto>>> deleteResults(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "합성완료사진 요청 모델")
            @RequestBody @Valid ResultPhotoRequestDto resultPhotoRequestDto
            ) {
        List<ResultPhoto> resultPhotos = new ArrayList<>();

        for (ResultPhotoDto resultPhotoDto : resultPhotoRequestDto.getResultPhotos()) {
            resultPhotos.add(
                    resultPhotoService.getResultPhoto(resultPhotoDto.getId())
            );
        }
        return ResponseEntity.ok(
                ApiResult.ok(
                        resultPhotoService.deleteResultPhotos(
                                        user,
                                        groupAlbumService.getGroupAlbumById(groupAlbumId),
                                        resultPhotos
                                )
                                .stream()
                                .map(resultPhotoMapper::toResponseDto)
                                .collect(Collectors.toList())
                )
        );
    }
}
