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
import org.apaas.utils.ArrayUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author ivan
 */
@Data
public class Query implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -96631836160269191L;
    
    /**
     * 查询的字段名列表
     */
    private Collection<String> fields;
    
    /**
     * 查询的条件组对象，可以包含多个条件组，条件组之间是或的关系
     */
    private Condition[] conditions = new Condition[0];
    
    /**
     * 排序条件语句
     */
    private Sorter[] sorters;
    
    /**
     * 分页对象
     */
    private PageRequest pageRequest = PageRequest.of();
    
    /**
     * 是否查询所有字段，如果为true，则忽略fields集合
     */
    private boolean allFields;
    
    /**
     * 是否递归, 是否按照Tree树递归查询
     */
    private boolean recursive;
    
    public Query() {
    }
    
    public Query(Collection<String> fields) {
        this.fields = fields;
    }
    
    public Query(Collection<String> fields, Sorter[] sorters) {
        this.fields = fields;
        this.sorters = sorters;
    }
    
    public Query(Collection<String> fields, Sorter[] sorters, Condition[] conditions) {
        this.fields = fields;
        this.sorters = sorters;
        this.conditions = conditions;
    }
    
    public Query(Collection<String> fields, Map<String, Object> params) {
        this.fields = fields;
        this.conditions = Condition.getConnections(params);
    }
    
    public Query(Collection<String> fields, Map<String, Object> params, Sorter[] sorters) {
        this.fields = fields;
        this.conditions = Condition.getConnections(params);
        this.sorters = sorters;
    }
    
    public Query(Condition[] conditions) {
        this.conditions = conditions;
    }
    
    public Query(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }
    
    public Query(Sorter[] sorters) {
        this.sorters = sorters;
    }
    
    public Query(Collection<String> fields, Condition[] conditions) {
        this.fields = fields;
        this.conditions = conditions;
    }
    
    public Query(PageRequest pageRequest, Condition[] conditions) {
        this.pageRequest = pageRequest;
        this.conditions = conditions;
    }
    
    public Query(PageRequest pageRequest, Sorter[] sorters, Condition[] conditions) {
        this.pageRequest = pageRequest;
        this.sorters = sorters;
        this.conditions = conditions;
    }
    
    public Query(int pageNumber, int pageSize, Condition[] conditions) {
        this.pageRequest = new PageRequest(pageNumber, pageSize);
        this.conditions = conditions;
    }
    
    public Query(Collection<String> fields, PageRequest pageRequest, Condition[] conditions) {
        this.fields = fields;
        this.pageRequest = pageRequest;
        this.conditions = conditions;
    }
    
    public Query(Collection<String> fields, PageRequest pageRequest, Sorter[] sorters, Condition[] conditions) {
        this.fields = fields;
        this.pageRequest = pageRequest;
        this.sorters = sorters;
        this.conditions = conditions;
    }
    
    public Query(Collection<String> fields, Condition[] conditions, PageRequest pageRequest, Sorter[] sorters, boolean recursive) {
        this.fields = fields;
        this.conditions = conditions;
        this.pageRequest = pageRequest;
        this.sorters = sorters;
        this.recursive = recursive;
    }
    
    public static Query builder() {
        return new Query();
    }
    
    public Collection<String> getFields() {
        if (isRecursive() && Objects.nonNull(fields) && !fields.contains("ancestors") && !fields.contains("count(1)")) {
            fields.add("ancestors");
        }
        return fields;
    }
    
    public PageRequest getPageRequest() {
        return Objects.isNull(pageRequest) ? new PageRequest(PageRequest.PAGE_NUMBER, PageRequest.MAX_PAGE_SIZE) : pageRequest;
    }
    
    public Sorter[] getSorts() {
        return sorters;
    }
    
    public void setSorts(Sorter[] sorters) {
        this.sorters = sorters;
    }
    
    public Query addFields(Collection<String> fields) {
        setFields(fields);
        return this;
    }
    
    public Query addConditions(Condition[] conditions) {
        if (Objects.isNull(conditions)) {
            return this;
        }
        setConditions(ArrayUtils.addAll(getConditions(), conditions));
        return this;
    }
    
    public Query addCondition(Condition condition) {
        if (Objects.isNull(condition)) {
            return this;
        }
        setConditions(ArrayUtils.addAll(getConditions(), new Condition[]{condition}));
        return this;
    }
    
    public Query addPageRequest(PageRequest pageRequest) {
        setPageRequest(pageRequest);
        return this;
    }
    
    public Query addSorts(Sorter[] sorters) {
        setSorts(sorters);
        return this;
    }
    
    public Query addRecursive(boolean recursive) {
        setRecursive(recursive);
        return this;
    }
    
}
