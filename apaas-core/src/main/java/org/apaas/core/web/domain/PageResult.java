package org.apaas.core.web.domain;

import org.springframework.data.domain.Page;

import java.io.Serial;
import java.util.List;

/**
 * 分页返回结果
 */
public class PageResult<T> extends AjaxResult {
    @Serial
    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    public static final String TOTAL_TAG = "total";

    /** 当前页数据 */
    public static final String ROWS_TAG = "rows";

    /** 初始化一个新创建的 PageResult 对象 */
    public PageResult() {
        super();
    }

    /** 初始化一个新创建的 PageResult 对象
     * @param total 总记录数
     * @param rows 当前页数据
     */
    public PageResult(long total, T rows) {
        super.put(TOTAL_TAG, total);
        super.put(ROWS_TAG, rows);
    }

    /** 构建分页返回结果
     * @param page 分页对象
     * @return 分页返回结果
     */
    public static <T> PageResult<List<T>> build(PageResult<T> page) {
        return new PageResult<>(page.getTotalElements(), page.getContent());
    }

    /** 构建分页返回结果
     * @param total 总记录数
     * @param rows 当前页数据
     * @return 分页返回结果
     */
    public static <T> PageResult<T> build(long total, T rows) {
        return new PageResult<>(total, rows);
    }
}