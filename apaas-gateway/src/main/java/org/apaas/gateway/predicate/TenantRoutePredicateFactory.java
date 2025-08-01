package org.apaas.gateway.predicate;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 租户路由断言工厂
 */
@Slf4j
@Component
public class TenantRoutePredicateFactory extends AbstractRoutePredicateFactory<TenantRoutePredicateFactory.Config> {

    private static final String TENANT_ID_HEADER = "X-Tenant-Id";

    public TenantRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("tenantId");
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return exchange -> {
            String tenantId = exchange.getRequest().getHeaders().getFirst(TENANT_ID_HEADER);
            if (tenantId == null || tenantId.isEmpty()) {
                return false;
            }
            boolean match = config.getTenantId().equals(tenantId);
            log.debug("Tenant predicate match: {}, tenantId: {}, config: {}", match, tenantId, config.getTenantId());
            return match;
        };
    }

    @Data
    public static class Config {
        private String tenantId;
    }
}