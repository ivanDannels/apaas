package org.apaas.system.entity;

import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 数据字典项实体类
 */
@Data
public class DataDictionaryItem extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    private Long dictionaryId;

    /**
     * 字典项编码
     */
    private String code;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}