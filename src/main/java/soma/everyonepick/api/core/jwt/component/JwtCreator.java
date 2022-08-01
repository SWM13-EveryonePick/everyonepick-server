package soma.everyonepick.api.core.jwt.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import soma.everyonepick.api.user.entity.User;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

/**
 * JWT 토큰 생성기
 */
@Component
@RequiredArgsConstructor
public class JwtCreator {
    @Value("${spring.jwt.access-token-valid-milliseconds}")
    private long ACCESS_TOKEN_VALID_MILLISECONDS;

    @Value("${spring.jwt.refresh-token-valid-milliseconds}")
    private long REFRESH_TOKEN_VALID_MILLISECONDS;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * JWT Access Token 발급
     * @param user 사용자
     * @return Jwt 토큰
     */
    public String createAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_VALID_MILLISECONDS);
    }

    /**
     * JWT Refresh Token 발급
     * @param user 사용자
     * @return Jwt 토큰
     */
    public String createRefreshToken(User user) {
        return createToken(user, REFRESH_TOKEN_VALID_MILLISECONDS);
    }

    private String createToken(User user, long expirationMillisecond) {
        Claims claims = Jwts.claims().setSubject(user.getClientId()); // claim 임의로 설정
        claims.put("nickname", user.getNickname());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMillisecond))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
