package soma.everyonepick.api.core.oauth2.service;

import soma.everyonepick.api.core.oauth2.dto.OAuth2Profile;

/**
 * {@link OAuth2Profile}을 Provider로부터 조회하는 서비스.
 */
public interface OAuth2Service {
    /**
     * Provider로부터 {@link OAuth2Profile}를 조회한다.
     * @param providerAccessToken Provider 제공 토큰
     * @return Provider에서 가져온 사용자 프로필
     */
    OAuth2Profile getProfile(String providerAccessToken);
}
