/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.core.query;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页对象
 *
 * @author ivan
 */
@Data
public class PageResult<T> implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 743188519634518206L;
    
    /**
     * 当前页码
     */
    private long current;
    
    /**
     * 每页条数
     */
    private long size;
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 总页数
     */
    private long pages;
    
    /**
     * 数据列表
     */
    private List<T> records = Collections.emptyList();
    
    public PageResult() {
    }
    
    /**
     * 构造方法，用于创建PageResult对象
     *
     * @param current 当前页码
     * @param size    每页显示的数量
     * @param total   总元素数量
     * @param records 当前页的数据列表
     */
    public PageResult(long current, long size, long total, List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
        if (size > 0) {
            pages = total % size == 0 ? total / size : total / size + 1;
        }
    }
    
    /**
     * 创建分页结果对象
     *
     * @param <T>         元素类型
     * @param number      当前页码
     * @param size        每页显示的数量
     * @param totalElements 总元素数量
     * @param list        当前页的数据列表
     * @return 分页结果对象
     */
    public static <T> PageResult<T> of(long number, long size, long totalElements, List<T> list) {
        return new PageResult<>(number, size, totalElements, list);
    }
    
    public boolean isFirstPage() {
        return current == 1;
    }
    
    public boolean isLastPage() {
        return current == pages;
    }
    
    public boolean isPrevPage() {
        return current > 1 && current <= pages;
    }
    
    public boolean isNextPage() {
        return current < pages;
    }
    
    public static <T> PageResult<T> of(List<T> list) {
        return new PageResult<>(1, list.size(), list.size(), list);
    }
}
