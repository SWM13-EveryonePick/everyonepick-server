package soma.everyonepick.api.core.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soma.everyonepick.api.core.jwt.component.JwtCreator;
import soma.everyonepick.api.core.jwt.component.JwtResolver;
import soma.everyonepick.api.core.jwt.component.JwtValidator;
import soma.everyonepick.api.core.jwt.dto.Jwt;
import soma.everyonepick.api.core.jwt.dto.ProviderToken;
import soma.everyonepick.api.core.oauth2.service.OAuth2Service;
import soma.everyonepick.api.core.oauth2.service.OAuth2ServiceFactory;
import soma.everyonepick.api.user.entity.RefreshToken;
import soma.everyonepick.api.user.entity.User;
import soma.everyonepick.api.user.repository.RefreshTokenRepository;
import soma.everyonepick.api.user.service.UserService;

/**
 * JWT 관련 로직
 */
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtCreator jwtCreator;
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;
    private final OAuth2ServiceFactory oAuth2ServiceFactory;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * JWT 토큰 발급
     * @param user 사용자 엔티티
     * @return jwt 토큰
     */
    @Transactional
    public Jwt issue(User user) {
        RefreshToken refreshTokenFromDb = refreshTokenRepository.findByUser(user)
                .orElse(RefreshToken.builder().user(user).build());

        String accessToken = jwtCreator.createAccessToken(user);
        String refreshToken = jwtCreator.createRefreshToken(user);
        refreshTokenFromDb.setValue(refreshToken);
        refreshTokenRepository.saveAndFlush(refreshTokenFromDb);
        return new Jwt(accessToken, refreshToken);
    }
}
