package soma.everyonepick.api.user.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.core.message.ErrorMessage;
import soma.everyonepick.api.user.component.UserMapper;
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
    private final UserMapper userMapper;

    @ApiOperation(value = "회원아이디로 특정 사용자 정보 가져오기", notes = "회원아이디로 특정 사용자 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<UserDto>> getUserByClientId(
            @ApiParam(value = "사용자 회원아이디", required = true)
            @RequestParam String clientId
    ) {
        User user = userService.findByClientId(clientId);
        if (user == null) {
            throw new ResourceNotFoundException(ErrorMessage.NOT_EXIST_USER);
        }
        return ResponseEntity.ok(
                ApiResult.ok(
                        userMapper.toDto(user))
        );
    }

    @ApiOperation(value = "userId로 특정 사용자 정보 가져오기", notes = "userId로 특정 사용자 정보를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공"),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult<UserDto>> getUserById(
            @ApiParam(value = "사용자 id", required = true)
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userMapper.toDto(userService.findById(userId)))
        );
    }
}
