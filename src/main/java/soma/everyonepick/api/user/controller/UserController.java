package soma.everyonepick.api.user.controller;


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
import soma.everyonepick.api.core.annotation.CurrentUser;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;
import soma.everyonepick.api.core.message.ErrorMessage;
import soma.everyonepick.api.user.component.UserMapper;
import soma.everyonepick.api.user.dto.FaceInfoDto;
import soma.everyonepick.api.user.dto.UserResponseDto;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.service.FaceInfoUploadService;
import soma.everyonepick.api.user.service.UserService;

import javax.validation.Valid;
import java.util.List;


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
    private final FaceInfoUploadService faceInfoUploadService;

    @Operation(description = "회원아이디로 특정 사용자 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("")
    public ResponseEntity<ApiResult<UserResponseDto>> getUserByClientId(
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
    public ResponseEntity<ApiResult<UserResponseDto>> getUserById(
            @Parameter(description = "사용자 id", required = true)
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userMapper.toDto(userService.findById(userId)))
        );
    }

    @Operation(description = "현재 로그인한 사용자 정보 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResult<UserResponseDto>> getUser(
            @Parameter(hidden = true)
            @CurrentUser User user
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(
                        userMapper.toDto(user))
        );
    }

    @Operation(description = "사용자 얼굴정보 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공"),
    })
    @PostMapping(
            value = "face-info",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResult<FaceInfoDto>> postFaceInfo(
            @Parameter(hidden = true)
            @CurrentUser User user,
            @Parameter(description = "업로드할 이미지")
            @RequestPart("image") @Valid MultipartFile image
    ) {
        return ResponseEntity.ok(
                ApiResult.ok(faceInfoUploadService.uploadFaceInfo(user, image))
        );
    }
}
