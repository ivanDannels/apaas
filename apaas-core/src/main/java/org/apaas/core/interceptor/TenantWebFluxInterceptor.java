package org.apaas.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.constant.Constants;
import org.apaas.core.context.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 多租户WebFlux拦截器
 * @author ivan
 */
@Slf4j
@Component
public class TenantWebFluxInterceptor implements WebFilter {

    private static final String TENANT_ID_HEADER = "X-Tenant-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            // 从请求头中获取租户ID
            String tenantIdStr = exchange.getRequest().getHeaders().getFirst(TENANT_ID_HEADER);
            Long tenantId = null;

            if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
                try {
                    tenantId = Long.parseLong(tenantIdStr);
                } catch (NumberFormatException e) {
                    log.warn("Invalid tenant ID format: {}", tenantIdStr);
                }
            }

            // 如果没有获取到租户ID，则使用默认租户ID
            if (tenantId == null) {
                tenantId = Constants.DEFAULT_TENANT_ID;
            }

            // 设置租户ID到上下文中
            TenantContext.setTenantId(tenantId);
            log.debug("Set tenant ID: {}", tenantId);

        } catch (Exception e) {
            log.error("Error in tenant interceptor", e);
            // 出现异常时使用默认租户ID
            TenantContext.setTenantId(Constants.DEFAULT_TENANT_ID);
        }

        // 继续处理请求
        return chain.filter(exchange)
                .doFinally(signalType -> {
                    // 请求结束后清除租户ID
                    TenantContext.clear();
                    log.debug("Clear tenant ID");
                });
    }
}