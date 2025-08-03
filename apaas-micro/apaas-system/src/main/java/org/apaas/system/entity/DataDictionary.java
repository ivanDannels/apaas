package org.apaas.system.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 数据字典类型实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("data_dictionary")
@EqualsAndHashCode(callSuper = true)
public class DataDictionary extends BaseEntity {

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