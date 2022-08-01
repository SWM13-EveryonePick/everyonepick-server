package soma.everyonepick.api.user.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.core.jwt.dto.Jwt;
import soma.everyonepick.api.core.jwt.dto.ProviderToken;
import soma.everyonepick.api.core.jwt.service.JwtService;
import soma.everyonepick.api.core.oauth2.service.OAuth2ServiceFactory;
import soma.everyonepick.api.user.service.UserSignUpService;

import javax.validation.Valid;

/**
 * 사용자 인증 관련 엔드포인트
 */
@Api(value = "사용자 인증 관련 엔드포인트", tags = "사용자 인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserAuthController {
    private final UserSignUpService signUpService;

    @ApiOperation(value = "사용자 등록 (JWT 발급)", notes = "사용자를 신규로 등록하고, JWT를 발급합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "등록 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<Jwt>> signup(
            @ApiParam(value = "프로바이더 액세스 토큰", required = true) @RequestBody @Valid ProviderToken providerToken) {
        return ResponseEntity.ok(ApiResult.ok(
                signUpService.signUp(providerToken)));
    }
}
