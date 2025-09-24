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
package org.apaas.domain.convert;

import org.apaas.core.constant.Constants;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页处理
 * @author ivan
 */
public class PageConverter {
    
    public static Map<String, Object> convertToMap(org.apaas.core.query.PageRequest pageRequest) {
        return Map.of(Constants.CURRENT, pageRequest.getPageNumber(), Constants.SIZE, pageRequest.getPageSize());
    }
    
    public static Pageable convertPageRequest(Query query) {
        Assert.notNull(query, "Query must not be null");
        Assert.notNull(query.getPageRequest(), "PageRequest must not be null");
        return PageRequest.of(query.getPageRequest().getPageNumber() - 1, query.getPageRequest().getPageSize(), convertSort(query));
    }
    
    public static <T> PageResult<T> convertPageResult(Page<T> page) {
        Assert.notNull(page, "Query must not be null");
        Assert.notNull(page.stream().toList(), "PageRequest must not be null");
        return PageResult.of(page.getNumber(), page.getSize(), page.getTotalElements(), page.getContent());
    }
    
    /**
     * 将分页结果集转化为DTO分页结果集
     *
     * @param converter 实体类到DTO的转换函数
     * @param <D>     DTO类型
     * @param <E>     实体类型
     * @return 转换后的分页结果集
     */
    public static <D, E> PageResult<D> convertToDTO(PageResult<E> pageResult, Function<E, D> converter) {
        List<D> dtoList = pageResult.getRecords().stream().map(converter).collect(Collectors.toList());
        return PageResult.of(pageResult.getCurrent(), pageResult.getSize(), pageResult.getTotal(), dtoList);
    }
    
    public static Sort convertSort(Query query) {
        Assert.notNull(query, "Query must not be null");
        return Sort.by(Arrays.stream(query.getSorts()).map(sort -> new Sort.Order(Sort.Direction.fromString(sort.getDirection().name()), sort.getField())).toList());
    }
}
