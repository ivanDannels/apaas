package org.apaas.core.event;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体变更事件
 * @param <T> 实体类型
 */
@Data
@NoArgsConstructor
public class EntityChangedEvent<T> {
    
    /**
     * 操作类型
     */
    private OperationType operationType;
    
    /**
     * 实体对象
     */
    private T entity;
    
    /**
     * 构造函数
     *
     * @param operationType 操作类型
     * @param entity        实体对象
     */
    public EntityChangedEvent(OperationType operationType, T entity) {
        this.operationType = operationType;
        this.entity = entity;
    }
    
    /**
     * 操作类型枚举
     */
    public enum OperationType {
        CREATE, UPDATE, DELETE
    }
}