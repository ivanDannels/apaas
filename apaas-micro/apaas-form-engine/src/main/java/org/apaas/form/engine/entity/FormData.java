package org.apaas.form.engine.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 表单数据
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("form_data")
public class FormData extends BaseEntity {

    /**
     * 表单编码
     */
    private String formCode;
    /**
     * 表单定义ID
     */
    private String formDefinitionId;
    /**
     *  数据
     */
    private String data;
    /**
     * 流程实例ID
     */
    private Long flowInstanceId;
    /**
     * 状态
     */
    private Integer status;

}
