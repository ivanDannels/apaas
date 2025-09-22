package org.apaas.core.web.domain;

import lombok.Data;

/**
 * 分页查询基类
 */
@Data
public class BasePageQuery {

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderByColumn;

    /**
     * 排序方向 "asc" 或 "desc"
     */
    private String orderDirection;

    /**
     * 获取排序信息
     */
    public String getOrderBy() {
        if (orderByColumn != null && !orderByColumn.isEmpty()) {
            return orderByColumn + " " + ("desc".equalsIgnoreCase(orderDirection) ? "desc" : "asc");
        }
        return null;
    }
}