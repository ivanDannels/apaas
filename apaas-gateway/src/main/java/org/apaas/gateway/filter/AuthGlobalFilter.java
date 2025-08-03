package org.apaas.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.constant.SecurityConstants;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证全局过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 跳过不需要认证的路径
        String path = request.getURI().getPath();
        if (isSkipAuth(path)) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = getToken(request);
        if (StrUtil.isEmpty(token)) {
            return setUnauthorizedResponse(response, "未提供令牌");
        }

        // 验证token
        try {
            // 检查token是否在黑名单
            Boolean isBlack = redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + token);
            if (Boolean.TRUE.equals(isBlack)) {
                return setUnauthorizedResponse(response, "令牌已失效");
            }

            String username = "";
            Long userId = 0L;

            // 获取用户权限
            List<String> permissions = (List<String>) redisTemplate.opsForValue().get(SecurityConstants.USER_PERMISSIONS_PREFIX + userId);
            if (permissions == null) {
                permissions = new ArrayList<>();
            }

            // 设置用户信息到上下文
            List<SimpleGrantedAuthority> authorities = permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));

        } catch (Exception e) {
            log.error("令牌验证失败: {}", e.getMessage());
            return setUnauthorizedResponse(response, "令牌验证失败");
        }
    }

    /**
     * 获取请求头中的token
     */
    private String getToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_HEADER);
        if (StrUtil.isNotEmpty(bearerToken) && bearerToken.startsWith(SecurityConstants.BEARER_PREFIX)) {
            return bearerToken.substring(SecurityConstants.BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * 设置未授权响应
     */
    private Mono<Void> setUnauthorizedResponse(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String body = String.format("{\"code\":401,\"msg\":\"%s\"}", message);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    /**
     * 判断是否跳过认证
     */
    private boolean isSkipAuth(String path) {
        // 跳过认证的路径
        String[] skipPaths = {
                // 认证相关
                "/auth/login", "/auth/logout", "/auth/oauth2/**",
                // 健康检查
                "/actuator/**",
                // Swagger文档
                "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"
        };

        for (String skipPath : skipPaths) {
            if (path.startsWith(skipPath.replace("**", ""))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return -100;
    }
}