package soma.everyonepick.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.component.DeviceTokenMapper;
import soma.everyonepick.api.user.dto.DeviceTokenDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.DeviceTokenService;

import javax.validation.Valid;

/**
 * FCM device token 관련 Endpoint.
 */

@Tag(description = "FCM device token 관련 Endpoint.", name = "Device Token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/device-tokens")
public class DeviceTokenController {
    private final DeviceTokenService deviceTokenService;
    private final DeviceTokenMapper deviceTokenMapper;

    @Operation(description = "FCM device token 갱신")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
    })
    @PostMapping(value = "")
    public ResponseEntity<ApiResult<DeviceTokenDto>> postDeviceToken(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "FCM device Token", required = true)
            @RequestBody @Valid DeviceTokenDto deviceTokenDto
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        deviceTokenMapper.toDto(
                                deviceTokenService.createDeviceToken(user, deviceTokenDto)
                        )
                )
        );
    }
}
