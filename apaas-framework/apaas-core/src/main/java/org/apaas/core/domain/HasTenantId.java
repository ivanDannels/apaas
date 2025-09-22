package org.apaas.core.domain;

/**
 * 标记接口，表示实体具有tenantId字段
 * @author ivan
 */
public interface HasTenantId {
    Long getTenantId();
    void setTenantId(Long tenantId);
}