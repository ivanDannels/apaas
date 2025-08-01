package org.apaas.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 租户过滤器
 */
@Slf4j
@Component
public class TenantFilter implements GlobalFilter, Ordered {

    private static final String TENANT_ID_HEADER = "X-Tenant-Id";
    private static final String DEFAULT_TENANT_ID = "1";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String tenantId = request.getHeaders().getFirst(TENANT_ID_HEADER);

        // 如果请求头中没有租户ID，则使用默认租户ID
        if (tenantId == null || tenantId.isEmpty()) {
            tenantId = DEFAULT_TENANT_ID;
        }

        // 将租户ID添加到请求头中
        ServerHttpRequest newRequest = request.mutate()
                .header(TENANT_ID_HEADER, tenantId)
                .build();

        log.debug("Set tenant ID: {}", tenantId);
        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }
}