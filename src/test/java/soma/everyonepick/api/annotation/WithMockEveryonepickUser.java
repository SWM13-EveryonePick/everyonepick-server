package soma.everyonepick.api.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;
import soma.everyonepick.api.config.WithMockEveryonepickUserSecurityContextFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockEveryonepickUserSecurityContextFactory.class)
public @interface WithMockEveryonepickUser {
    String clientId() default "testClientId";
}
