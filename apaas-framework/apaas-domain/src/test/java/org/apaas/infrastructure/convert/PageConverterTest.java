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
package org.apaas.infrastructure.convert;

import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.query.Sorter;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.apaas.core.query.Direction.ASC;
import static org.apaas.core.query.Direction.DESC;
import static org.junit.jupiter.api.Assertions.*;

/**
 * PageConverter测试类
 * @author ivan
 */
class PageConverterTest {
    
    @Test
    void testConvertToMap() {
        // Given
        org.apaas.core.query.PageRequest pageRequest = new org.apaas.core.query.PageRequest(1, 10);
        
        // When
        Map<String, Object> result = PageConverter.convertToMap(pageRequest);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.get("current"));
        assertEquals(10, result.get("size"));
    }
    
    @Test
    void testConvertPageRequest() {
        // Given
        Sorter sort = new Sorter();
        sort.setField("id");
        sort.setDirection(ASC);
        
        org.apaas.core.query.PageRequest pageRequest = new org.apaas.core.query.PageRequest(1, 10);
        Query query = new Query();
        query.setPageRequest(pageRequest);
        query.setSorts(new Sorter[]{sort});
        
        // When
        Pageable pageable = PageConverter.convertPageRequest(query);
        
        // Then
        assertNotNull(pageable);
        assertEquals(0, pageable.getPageNumber()); // PageRequest中页码从0开始
        assertEquals(10, pageable.getPageSize());
        assertFalse(pageable.getSort().isEmpty());
    }
    
    @Test
    void testConvertPageResult() {
        // Given
        List<String> content = Arrays.asList("item1", "item2", "item3");
        PageImpl<String> page = new PageImpl<>(content, PageRequest.of(0, 10), 3);
        
        // When
        PageResult<String> result = PageConverter.convertPageResult(page);
        
        // Then
        assertNotNull(result);
        assertEquals(0, result.getCurrent()); // PageResult中页码从1开始
        assertEquals(10, result.getSize());
        assertEquals(3, result.getTotal());
        assertEquals(content, result.getRecords());
    }
    
    @Test
    void testConvertToDTO() {
        // Given
        List<String> content = Arrays.asList("item1", "item2", "item3");
        PageResult<String> pageResult = PageResult.of(1, 10, 3, content);
        
        // When
        PageResult<Integer> result = PageConverter.convertToDTO(pageResult, String::length);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getCurrent());
        assertEquals(10, result.getSize());
        assertEquals(3, result.getTotal());
        assertEquals(Arrays.asList(5, 5, 5), result.getRecords()); // "item1".length() = 5
    }
    
    @Test
    void testConvertSort() {
        // Given
        Sorter sort1 = new Sorter();
        sort1.setField("id");
        sort1.setDirection(ASC);
        
        Sorter sort2 = new Sorter();
        sort2.setField("name");
        sort2.setDirection(DESC);
        
        Query query = new Query();
        query.setSorts(new Sorter[]{sort1, sort2});
        
        // When
        Sort sort = PageConverter.convertSort(query);
        
        // Then
        assertNotNull(sort);
        assertFalse(sort.isEmpty());
        assertEquals(2, sort.stream().count());
        
        Sort.Order order1 = sort.getOrderFor("id");
        Sort.Order order2 = sort.getOrderFor("name");
        
        assertNotNull(order1);
        assertEquals(Direction.ASC, order1.getDirection());
        
        assertNotNull(order2);
        assertEquals(Direction.DESC, order2.getDirection());
    }
}