package org.apaas.core.context;

import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
public class TenantContext {

    private static final ThreadLocal<Long> TENANT_ID_CONTEXT = new ThreadLocal<>();
    
    public static void setTenantId(Long tenantId) {
        TENANT_ID_CONTEXT.set(tenantId);
    }
    
    public static Long getTenantId() {
        return TENANT_ID_CONTEXT.get();
    }
    
    public static void clear() {
        TENANT_ID_CONTEXT.remove();
    }
    
    public static Mono<Long> getTenantIdAsync() {
        return Mono.justOrEmpty(getTenantId());
    }
}