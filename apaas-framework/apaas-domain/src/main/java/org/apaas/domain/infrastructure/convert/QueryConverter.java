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
package org.apaas.domain.infrastructure.convert;

import org.apaas.domain.domain.exception.BusinessException;
import org.apaas.core.query.Condition;
import org.apaas.core.query.Query;
import org.apaas.utils.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ivan
 */
public class QueryConverter {
    
    public static Map<String, Object> convertToMap(Query query) {
        Map<String, Object> queryParams = Arrays.stream(query.getConditions()).collect(Collectors.toMap(Condition::getField, Condition::getValue));
        Map<String, Object> pageParams = PageConverter.convertToMap(query.getPageRequest());
        return Stream.concat(queryParams.entrySet().stream(), pageParams.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue));
    }
    
    public static <T> T convertToEntity(Query query, Class<T> entityClass) {
        try {
            // 根据 entityClass 得到一个 T的实例
            T entity = entityClass.getDeclaredConstructor().newInstance();
            Condition[] conditions = query.getConditions();
            if (conditions != null) {
                // 循环遍历条件数组, 并处理每个条件转换为实体属性
                for (Condition condition : conditions) {
                    // 获取实体属性名
                    String fieldName = condition.getField();
                    // 获取实体属性值
                    Object fieldValue = condition.getValue();
                    // 根据实体属性名获取实体属性
                    Field field = ReflectionUtils.findField(entityClass, fieldName);
                    // 设置实体属性值
                    if (Objects.nonNull(field)) {
                        ReflectionUtils.makeAccessible(field);
                        ReflectionUtils.setField(field, entity, fieldValue);
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new BusinessException("Failed to convert query to entity", e);
        }
    }
    
    public static <T> Example<T> convertToExample(Query query, Class<T> entityClass) {
        return Example.of(convertToEntity(query, entityClass));
    }
    
    private static <T> String getColumnByFieldName(Class<T> entityClass, String fieldName) {
        try {
            // 根据实体属性名获取实体属性
            Field field = ReflectionUtils.findField(entityClass, fieldName);
            if (Objects.nonNull(field)) {
                Column column = field.getAnnotation(Column.class);
                if (column == null || column.value().isEmpty()) {
                    return StringUtils.toUnderScoreCase(fieldName);
                }
                return column.value();
            }
            return fieldName;
        } catch (Exception e) {
            throw new BusinessException("Failed to get field: " + fieldName, e);
        }
    }
}