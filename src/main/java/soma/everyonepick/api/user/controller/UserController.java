package soma.everyonepick.api.user.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.user.dto.UserDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.UserService;

import java.util.List;

/**
 * 사용자 정보 관련 Endpoint.
 */

@Api(value = "사용자 정보 관련 Endpoint", tags = "사용자 정보")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "특정 사용자의 프로필 가져오기", notes = "특정 사용자의 프로필 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/{clientId}")
    public ResponseEntity<ApiResult<UserDto>> getUserByClientId(
            @ApiParam(value = "사용자 회원아이디", required = true)
            @PathVariable String clientId
    ) {
        return ResponseEntity.ok(ApiResult.ok(userService.findByClientId(clientId)));
    }
}
