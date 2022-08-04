package soma.everyonepick.api.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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


/**
 * 사용자 정보 관련 Endpoint.
 */

@Tag(description = "사용자 정보 관련 Endpoint", name = "사용자 정보")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(description = "회원아이디로 특정 사용자 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<UserDto>> getUserByClientId(
            @Parameter(description = "사용자 회원아이디", required = true)
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

    @Operation(description = "userId로 특정 사용자 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult<UserDto>> getUserById(
            @Parameter(description = "사용자 id", required = true)
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userMapper.toDto(userService.findById(userId)))
        );
    }
}
