package org.apaas.form.engine.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apaas.domain.entity.BaseEntity;

/**
 * 表单实例实体
 * @author ivan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FormInstance extends BaseEntity {
    /**
     * 表单定义ID
     */
    private Long formDefinitionId;
    
    /**
     * 表单数据 (JSON格式)
     */
    private String dataJson;
    
    /**
     * 提交人
     */
    private String submitter;
    
    /**
     * 提交时间
     */
    private java.time.LocalDateTime submitTime;
}