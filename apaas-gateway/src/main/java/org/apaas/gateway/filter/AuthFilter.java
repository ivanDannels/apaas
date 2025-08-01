package org.apaas.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 认证过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTH_HEADER = "Authorization";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";

    /**
     * 白名单路径，不需要认证
     */
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/auth/login",
            "/auth/captcha",
            "/auth/register",
            "/actuator",
            "/swagger-ui",
            "/swagger-resources",
            "/v3/api-docs",
            "/webjars",
            "/doc.html"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();

        // 白名单路径直接放行
        if (isWhitePath(path)) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = request.getHeaders().getFirst(AUTH_HEADER);
        if (StrUtil.isBlank(token) || !token.startsWith(TOKEN_PREFIX)) {
            log.warn("Token is missing or invalid: {}", token);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // 解析token，这里简化处理，实际应该调用认证服务验证token
        // 在实际项目中，应该调用认证服务验证token，并获取用户信息
        // 这里简化处理，假设token有效，并从token中提取用户ID和用户名
        String userId = "1";
        String userName = "admin";

        // 将用户信息添加到请求头中，传递给下游服务
        ServerHttpRequest newRequest = request.mutate()
                .header(USER_ID_HEADER, userId)
                .header(USER_NAME_HEADER, userName)
                .build();

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return -50;
    }

    /**
     * 判断是否为白名单路径
     */
    private boolean isWhitePath(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }
}