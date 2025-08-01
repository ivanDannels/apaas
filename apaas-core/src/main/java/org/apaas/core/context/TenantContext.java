package org.apaas.core.context;

import reactor.core.publisher.Mono;

public class TenantContext {
    private static final ThreadLocal<Long> tenantIdContext = new ThreadLocal<>();
    
    public static void setTenantId(Long tenantId) {
        tenantIdContext.set(tenantId);
    }
    
    public static Long getTenantId() {
        return tenantIdContext.get();
    }
    
    public static void clear() {
        tenantIdContext.remove();
    }
    
    public static Mono<Long> getTenantIdAsync() {
        return Mono.justOrEmpty(getTenantId());
    }
}