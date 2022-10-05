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
import soma.everyonepick.api.album.dto.PickDto;
import soma.everyonepick.api.album.dto.PickInfoResponseDto;
import soma.everyonepick.api.album.entity.PickInfoUser;
import soma.everyonepick.api.album.service.PickInfoService;
import soma.everyonepick.api.album.service.PickService;
import soma.everyonepick.api.core.dto.ApiResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 사진선택 정보 관련 Endpoint
 */
@Tag(description = "사진선택 정보 관련 Endpoint", name = "사진선택 정보")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album/{groupAlbumId}/pick/{pickId}/pick-info")
public class PickInfoController {
    private final PickInfoMapper pickInfoMapper;
    private final PickInfoService pickInfoService;
    private final PickService pickService;

    @Operation(description = "단체앨범 사진선택 정보 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping(value = "")
    public ResponseEntity<ApiResult<PickInfoResponseDto>> getPickInfo(
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
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
            @Parameter(description = "단체앨범 id", required = true)
            @PathVariable Long groupAlbumId,
            @Parameter(description = "사진선택 작업 id", required = true)
            @PathVariable Long pickId
    ) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(6L);
        userIds.add(7L);

        PickInfoUser pickInfoUser = PickInfoUser
                .builder()
                .pickId(pickId.toString())
                .timeOut(3600L)
                .userIds(userIds)
                .build();

        return ResponseEntity.ok(
                ApiResult.ok(
                        pickInfoMapper.toDto(
                                pickInfoService.createPickInfo(pickInfoUser)
                        )
                )
        );
    }
}
