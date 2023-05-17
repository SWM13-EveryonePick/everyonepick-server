package soma.everyonepick.api.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import soma.everyonepick.api.annotation.WithMockEveryonepickUser;
import soma.everyonepick.api.core.oauth2.dto.SecurityUser;
import soma.everyonepick.api.user.entity.User;

public class WithMockEveryonepickUserSecurityContextFactory implements WithSecurityContextFactory<WithMockEveryonepickUser> {

        @Override
        public SecurityContext createSecurityContext(WithMockEveryonepickUser annotation) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            SecurityUser securityUser = new SecurityUser(
                    User.builder()
                            .clientId(annotation.clientId())
                            .build()
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, securityUser.getPassword(), securityUser.getAuthorities());
            context.setAuthentication(authentication);

            return context;
        }
}
