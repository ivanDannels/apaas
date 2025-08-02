package org.apaas.system.entity;


import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 数据字典类型实体类
 */
@Data

public class DataDictionary extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典类型：0-系统字典，1-业务字典
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