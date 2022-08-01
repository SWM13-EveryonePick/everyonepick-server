package soma.everyonepick.api.core.jwt.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import soma.everyonepick.api.core.exception.ResourceNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtResolver jwtResolver;
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtResolver.resolveToken(servletRequest);

        if (jwtValidator.validateToken(token)) {
            try {
                setAuthToSecurityContextHolder(token);
            } catch (ResourceNotFoundException e) {
                log.error("토큰에 해당하는 사용자가 없습니다.", e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setAuthToSecurityContextHolder(String token) {
        Authentication auth = jwtResolver.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
