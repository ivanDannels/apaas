package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 数据字典项实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("data_dictionary_item")
@EqualsAndHashCode(callSuper = true)
public class DataDictionaryItem extends BaseEntity {

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
    private Integer sequence;

    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;
}