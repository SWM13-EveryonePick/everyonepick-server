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
import soma.everyonepick.api.core.dto.ApiResult;
import soma.everyonepick.api.core.jwt.dto.Jwt;
import soma.everyonepick.api.core.jwt.dto.ProviderToken;
import soma.everyonepick.api.core.jwt.dto.RefreshDto;
import soma.everyonepick.api.core.jwt.service.JwtService;
import soma.everyonepick.api.user.service.UserSignUpService;

import javax.validation.Valid;

/**
 * 사용자 인증 관련 엔드포인트
 */
@Tag(description = "사용자 인증 관련 엔드포인트", name = "사용자 인증")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    private final UserSignUpService signUpService;
    private final JwtService jwtService;

    @Operation(description = "사용자를 신규로 등록하고, JWT를 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResult<Jwt>> signup(
            @Parameter(description = "프로바이더 액세스 토큰", required = true)
            @RequestBody @Valid ProviderToken providerToken) {
        return ResponseEntity.ok(ApiResult.ok(
                signUpService.signUp(providerToken)));
    }

    @Operation(description = "Refresh Token을 이용하여 새로운 Access Token을 발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/refresh")
    public ResponseEntity<ApiResult<Jwt>> refresh(
            @Parameter(description = "Everyonepick Refresh Token", required = true)
            @RequestBody @Valid RefreshDto refreshToken) {
        return ResponseEntity.ok(ApiResult.ok(
                jwtService.tokenRefresh(refreshToken.getEveryonepickRefreshToken())));
    }
}
