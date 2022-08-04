package soma.everyonepick.api.core.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("everyonepick-api-public")
                .pathsToMatch("/api/auth/**")
                .addOpenApiCustomiser(buildOpenApi())
                .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("everyonepick-api-private")
                .pathsToMatch("/api/**")
                .pathsToExclude("/api/auth/**")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        return OpenApi -> OpenApi
                .info(new Info().title("모두의 Pick REST API")
                        .description("모두의 Pick REST API 문서")
                        .version("v1.0"))
                //.license(new License().name("GPL 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("모두의 Pick Gitlab")
                        .url("https://git.swmgit.org/swm-13-main/13_swm63/everyonepick-server"))
                .addSecurityItem(new SecurityRequirement().addList("jwt token"))
                .getComponents().addSecuritySchemes("jwt token", securityScheme);
    }

    public OpenApiCustomiser buildOpenApi() {

        return OpenApi -> OpenApi
                .info(new Info().title("모두의 Pick REST API")
                        .description("모두의 Pick REST API 문서")
                        .version("v1.0"))
                //.license(new License().name("GPL 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("모두의 Pick Gitlab")
                        .url("https://git.swmgit.org/swm-13-main/13_swm63/everyonepick-server"));
    }
}
