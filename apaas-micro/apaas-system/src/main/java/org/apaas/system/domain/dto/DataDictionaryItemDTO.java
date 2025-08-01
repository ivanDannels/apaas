package org.apaas.system.domain.dto;

import lombok.Data;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * 数据字典项查询DTO
 */
@Data
public class DataDictionaryItemDTO extends BasePageQuery {
    /**
     * 字典ID
     */
    private Long dictionaryId;

    /**
     * 字典项名称
     */
    private String name;

    /**
     * 字典项编码
     */
    private String code;

    /**
     * 状态：0-正常，1-停用
     */
    private Integer status;
}