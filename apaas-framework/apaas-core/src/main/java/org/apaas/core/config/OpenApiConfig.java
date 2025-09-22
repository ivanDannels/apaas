package org.apaas.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI配置类
 */
@Configuration
public class OpenApiConfig {

    /**
     * 配置OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("APaaS平台API文档")
                        .description("APaaS平台API文档")
                        .version("1.0.0")
                        .contact(new Contact().name("APaaS Team").email("apaas@example.com"))
                        .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }

    /**
     * 系统服务API分组
     */
    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder()
                .group("系统服务")
                .pathsToMatch("/system/**")
                .build();
    }

    /**
     * 权限中心API分组
     */
    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("权限中心")
                .pathsToMatch("/auth/**")
                .build();
    }

    /**
     * 流程引擎API分组
     */
    @Bean
    public GroupedOpenApi flowEngineApi() {
        return GroupedOpenApi.builder()
                .group("流程引擎")
                .pathsToMatch("/flow/**")
                .build();
    }

    /**
     * 调度任务API分组
     */
    @Bean
    public GroupedOpenApi jobApi() {
        return GroupedOpenApi.builder()
                .group("调度任务")
                .pathsToMatch("/job/**")
                .build();
    }

    /**
     * 集成服务API分组
     */
    @Bean
    public GroupedOpenApi integrationApi() {
        return GroupedOpenApi.builder()
                .group("集成服务")
                .pathsToMatch("/integration/**")
                .build();
    }

    /**
     * 报表服务API分组
     */
    @Bean
    public GroupedOpenApi reportApi() {
        return GroupedOpenApi.builder()
                .group("报表服务")
                .pathsToMatch("/report/**")
                .build();
    }

    /**
     * 开发平台API分组
     */
    @Bean
    public GroupedOpenApi designPlatformApi() {
        return GroupedOpenApi.builder()
                .group("开发平台")
                .pathsToMatch("/design/**")
                .build();
    }
}