package org.apaas.system.domain.dto;

import lombok.Data;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * 数据字典查询DTO
 */
@Data
public class DataDictionaryDTO extends BasePageQuery {
    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典类型：0-系统字典，1-业务字典
     */
    private Integer type;

    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
}