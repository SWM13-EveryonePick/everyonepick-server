package soma.everyonepick.api.pose.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.pose.component.PoseMapper;
import soma.everyonepick.api.pose.dto.PoseDto;
import soma.everyonepick.api.pose.service.PoseService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 단체포즈 관련 Endpoint
 */
@Tag(description = "단체포즈 관련 Endpoint", name = "단체포즈")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pose")
public class PoseController {
    private final PoseService poseService;
    private final PoseMapper poseMapper;

    @Operation(description = "단체포즈 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<List<PoseDto>>> getPoses(
            @Parameter(description = "인원수", required = true)
            @RequestParam String peopleNum
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        poseService.getPosesByPeopleNum(peopleNum).stream()
                                .map(poseMapper::toDto)
                                .collect(Collectors.toList())
                )
        );
    }
}
