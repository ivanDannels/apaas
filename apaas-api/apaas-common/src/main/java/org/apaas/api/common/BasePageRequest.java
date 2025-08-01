package org.apaas.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询基础类
 */
@Data
public class BasePageRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 页码，默认为1
     */
    private int pageNum = 1;

    /**
     * 每页条数，默认为10
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式：asc或desc
     */
    private String sortOrder;
}