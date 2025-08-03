package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 参数配置实体类
 */
@Data
@SuperBuilder
@Table("sys_config")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends BaseEntity {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数键
     */
    private String configKey;

    /**
     * 参数编码
     */
    private String code;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数类型：0-系统参数，1-业务参数
     */
    private Integer type;

    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}