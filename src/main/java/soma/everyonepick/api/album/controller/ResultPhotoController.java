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
import soma.everyonepick.api.album.component.ResultPhotoMapper;
import soma.everyonepick.api.album.dto.ResultPhotoDto;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.album.service.ResultPhotoService;
import soma.everyonepick.api.core.dto.ApiResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 합성완료 사진 관련 Endpoint
 */
@Tag(description = "합성완료 사진 관련 Endpoint", name = "합성완료 사진")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album/{groupAlbumId}/pick/{pickId}/result")
public class ResultPhotoController {
    private final ResultPhotoService resultPhotoService;
    private final PickService pickService;
    private final ResultPhotoMapper resultPhotoMapper;

    @Operation(description = "합성완료 사진조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<ResultPhotoDto>>> getResultPhotos(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "사진선택 작업 id", required = true)
            @PathVariable Long pickId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        resultPhotoService.getResultPhotosByPick(
                                pickService.getPickById(pickId)
                        ).stream()
                                .map(resultPhotoMapper::toDto)
                                .collect(Collectors.toList())
                )
        );
    }
}
