package org.apaas.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<T> list;

    /**
     * 页码
     */
    private int pageNum;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> build(long total, List<T> list, int pageNum, int pageSize) {
        int pages = (int) Math.ceil((double) total / pageSize);
        boolean hasPreviousPage = pageNum > 1;
        boolean hasNextPage = pageNum < pages;

        return new PageResult<>(total, list, pageNum, pageSize, pages, hasPreviousPage, hasNextPage);
    }
}