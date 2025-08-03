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

/**
 * 操作符枚举
 * @author ivan
 */
public interface Operator {
    
    /**
     * 比较操作符枚举
     */
    enum Comparison {
        
        /**
         * 等于（= 或 ==）：检查两侧的值是否相等。
         */
        EQ,
        /**
         * 不等于（!= 或 <>）：检查两侧的值是否不相等。
         */
        NEQ,
        /**
         * 大于（>）：检查左侧的值是否大于右侧的值。
         */
        GT,
        /**
         * 小于（<）：检查左侧的值是否小于右侧的值。
         */
        LT,
        /**
         * 大于等于（>=）：检查左侧的值是否大于或等于右侧的值。
         */
        GTE,
        /**
         * 小于等于（<=）：检查左侧的值是否小于或等于右侧的值。
         */
        LTE,
        /**
         *通配符和模式匹配,用于模式匹配查询，可以包含通配符 %（代表任意数量的字符）和 _（代表单个字符）。
         */
        LIKE,
        /**
         * 包含，用于检查一个值是否在一个列表或另一个查询结果中。
         */
        NOT_LIKE,
        /**
         * 不包含，用于检查一个值是不否在一个列表或另一个查询结果中。
         */
        IN,
        /**
         * 不包含，用于检查一个值是否不在一个列表或另一个查询结果中。
         */
        NOT_IN,
        /**
         * 存在，用于检查子查询是否返回至少一行数据。
         */
        EXISTS,
        /**
         * 不存在，用于检查子查询是否一条数据都没有。
         */
        NOT_EXISTS,
        /**
         * 为空，用于检查一个字段是否为NULL。
         */
        IS_NULL,
        /**
         * 非空，用于检查一个字段是否为非NULL。
         */
        IS_NOT_NULL,
        /**
         * 介于，用于检查一个值是否在指定的范围内，包括范围的两端。
         */
        BETWEEN,
        /**
         * 不介于，用于检查一个值是否不在指定的范围内，不包括范围的两端。
         */
        NOT_BETWEEN;
        
        /**
         * 根据字符串值返回对应{@link Comparison}值
         *
         * @param value 排序方式字符串，只能是EQ，NEQ，GT，LT，GTE，LTE，LIKE，IN，NOT_IN，EXISTS，NOT_EXISTS，IS_NULL，IS_NOT_NULL，BETWEEN，NOT_BETWEEN
         * @return {@link Comparison}
         * @throws IllegalArgumentException 如果给定值无法解析为枚举值。
         */
        public static Comparison fromString(String value) throws IllegalArgumentException {
            try {
                return Comparison.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Invalid value [%s] for orders given! Has to be either EQ，NEQ，GT，LT，GTE，LTE，LIKE，IN，NOT_IN，EXISTS，NOT_EXISTS，IS_NULL，IS_NOT_NULL，BETWEEN，NOT_BETWEEN (case insensitive).", value), e);
            }
        }
    }
    
    /**
     * 逻辑操作符枚举
     */
    enum Logical {
        
        /**
         * 逻辑与，用于连接多个条件，所有条件都必须为真。
         */
        AND,
        /**
         * 逻辑或，用于连接多个条件，只要有一个条件为真即可。
         */
        OR,
        /**
         * 逻辑非，用于连接多个条件，所有条件都必须为假。
         */
        NOT;
        
        /**
         * 根据字符串值返回对应{@link Logical}值
         *
         * @param value 排序方式字符串，只能是AND，OR，NOT
         * @return {@link Logical}
         * @throws IllegalArgumentException 如果给定值无法解析为枚举值。
         */
        public static Logical fromString(String value) throws IllegalArgumentException {
            try {
                return Logical.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Invalid value [%s] for orders given! Has to be either AND，OR，NOT (case insensitive).", value), e);
            }
        }
    }
    
}
