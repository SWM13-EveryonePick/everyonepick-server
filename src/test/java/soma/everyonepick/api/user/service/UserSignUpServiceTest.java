package soma.everyonepick.api.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import soma.everyonepick.api.core.jwt.dto.Jwt;
import soma.everyonepick.api.core.jwt.dto.ProviderToken;
import soma.everyonepick.api.core.jwt.service.JwtService;
import soma.everyonepick.api.core.oauth2.dto.OAuth2Profile;
import soma.everyonepick.api.core.oauth2.service.OAuth2Service;
import soma.everyonepick.api.core.oauth2.service.OAuth2ServiceFactory;
import soma.everyonepick.api.user.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserSignUpServiceTest {

    @Mock
    UserService userService;

    @Mock
    OAuth2ServiceFactory oAuth2ServiceFactory;

    @Mock
    JwtService jwtService;

    @Mock
    OAuth2Service oAuth2Service;

    @InjectMocks
    UserSignUpService userSignUpService;

    ProviderToken providerToken;
    OAuth2Profile oAuth2Profile;
    User user;
    Jwt jwt;

    @BeforeEach
    void setUp() {
        providerToken = new ProviderToken();
        providerToken.setProviderName("providerName");
        providerToken.setProviderAccessToken("providerAccessToken");

        oAuth2Profile = new OAuth2Profile();
        oAuth2Profile.setClientId("clientId");
        oAuth2Profile.setNickname("nickname");
        oAuth2Profile.setThumbnailImageUrl("thumbnailImageUrl");

        user = User.builder().build();
        jwt = new Jwt();
    }

    @Test
    void signUpTest() {
        when(oAuth2ServiceFactory.getInstance(any(String.class))).thenReturn(oAuth2Service);
        when(oAuth2Service.getProfile(any(String.class))).thenReturn(oAuth2Profile);
        when(userService.findByClientId(any(String.class))).thenReturn(null);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(jwtService.issue(any(User.class))).thenReturn(jwt);

        Jwt result = userSignUpService.signUp(providerToken);
        assertEquals(jwt, result);

        verify(oAuth2ServiceFactory).getInstance(providerToken.getProviderName());
        verify(oAuth2Service).getProfile(providerToken.getProviderAccessToken());
        verify(userService).findByClientId(oAuth2Profile.getClientId());
        verify(userService).saveUser(any(User.class));
        verify(jwtService).issue(any(User.class));
    }
}
