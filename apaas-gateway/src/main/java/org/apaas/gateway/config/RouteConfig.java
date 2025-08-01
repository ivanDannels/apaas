package org.apaas.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 路由配置
 */
@Slf4j
@Configuration
public class RouteConfig {

    /**
     * 配置路由
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 健康检查路由
                .route("health_check", r -> r.path("/actuator/**")
                        .uri("lb://apaas-gateway"))
                .build();
    }
}