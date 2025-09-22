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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 封装规范查询条件
 *
 * @author ivan
 */
@Data
public class Condition implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -5423475322959741024L;
    
    /**
     * 逻辑运算符
     */
    private Operator.Logical logical = Operator.Logical.AND;
    
    /**
     * 字段名
     */
    private String field;
    
    /**
     * 字段类型
     */
    private Class<?> fieldType;
    
    /**
     * 字段值
     * 1.如果为 BETWEEN 和 NOT_BETWEEN，则第一个值为 firstValue，第二个值为 secondValue的数组，否则为 value;
     * 2.如果为 IS_NULL 和 NOT_NULL，则 value 为 null
     * 3.如果为 IN 和 NOT_IN，则 value 为数组
     * 4.如果为 LIKE 和 NOT_LIKE，则 value 为字符串
     * 5.如果为 EXISTS 和 NOT_EXISTS，则 value 为 true 或 false
     * 6.如果为 IS_NOT_NULL，则 value 为 true 或 false
     */
    private Object value;
    
    /**
     * 字段操作符
     */
    private Operator.Comparison comparison = Operator.Comparison.EQ;
    
    public Condition() {
    }
    
    public Condition(String field, Object value) {
        this.field = field;
        this.value = value;
    }
    
    public Condition(String field, Operator.Comparison comparison, Object value) {
        this.field = field;
        this.comparison = comparison;
        this.value = value;
    }
    
    public Condition(String field, String comparison, Object value) {
        this.field = field;
        this.comparison = Operator.Comparison.fromString(comparison);
        this.value = value;
    }
    
    public Condition(String logic, String field, String comparison, Object value) {
        this.logical = Operator.Logical.fromString(logic);
        this.field = field;
        this.comparison = Operator.Comparison.fromString(comparison);
        this.value = value;
    }
    
    /**
     * 解析为Condition
     *
     * @param field  字段名
     * @param value 普通值
     * @return Condition
     */
    public static Condition eq(String field, Object value) {
        return new Condition(field, value);
    }
    
    /**
     * 解析为Condition
     *
     * @param field    字段名
     * @param comparison 操作符
     * @param value    值
     * @return Condition
     */
    public static Condition of(String field, String comparison, Object value) {
        return new Condition(field, comparison, value);
    }
    
    public static Condition between(String field, Object firstValue, Object secondValue) {
        return new Condition(field, Operator.Comparison.BETWEEN.name(), new Object[]{firstValue, secondValue});
    }
    
    public static Condition[] getConnections(Map<String, Object> params) {
        if (params.isEmpty()) {
            return null;
        }
        List<Condition> conditions = new ArrayList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            conditions.add(new Condition(entry.getKey(), entry.getValue()));
        }
        return conditions.toArray(Condition[]::new);
    }
    
}
