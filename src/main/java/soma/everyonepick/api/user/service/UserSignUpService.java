package soma.everyonepick.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import soma.everyonepick.api.core.jwt.dto.Jwt;
import soma.everyonepick.api.core.jwt.dto.ProviderToken;
import soma.everyonepick.api.core.jwt.service.JwtService;
import soma.everyonepick.api.core.oauth2.dto.OAuth2Profile;
import soma.everyonepick.api.core.oauth2.service.OAuth2ServiceFactory;
import soma.everyonepick.api.user.entity.User;

import javax.validation.Valid;

@Validated
@Service
@RequiredArgsConstructor
public class UserSignUpService {
    private final UserService userService;
    private final OAuth2ServiceFactory oAuth2ServiceFactory;
    private final JwtService jwtService;

    /**
     * 사용자 등록
     * @param providerToken 프로바이더에서 제공하는 토큰 정보
     * @return jwt 토큰
     */
    @Transactional
    public Jwt signUp(@Valid ProviderToken providerToken) {
        OAuth2Profile oAuth2Profile = oAuth2ServiceFactory.getInstance(providerToken.getProviderName())
                .getProfile(providerToken.getProviderAccessToken());

        String clientId = oAuth2Profile.getClientId();
        User user = userService.findByClientId(clientId);

        if(user == null) {
            user = User.builder().build();
        }

        user.setClientId(oAuth2Profile.getClientId());
        user.setNickname(oAuth2Profile.getNickname());
        user.setThumbnailImageUrl(oAuth2Profile.getThumbnailImageUrl());

        user = userService.saveUser(user);

        return jwtService.issue(user);
    }
}
