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

import org.apaas.core.query.Direction;
import org.apaas.core.query.Operator;
import org.apaas.domain.exception.BusinessException;
import org.apaas.core.query.Condition;
import org.apaas.core.query.Query;
import org.apaas.utils.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
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

    public static <T> Sort convertToSort(Query query, Class<T> entityClass) {
        List<Sort.Order> orders = Arrays.stream(query.getSorts()).map(order -> Sort.Order.by(getColumnByFieldName(entityClass, order.getField())).with(order.getDirection() == Direction.ASC ? Sort.Direction.ASC : Sort.Direction.DESC)).toList();
        return Sort.by(orders);
    }

    public static <T> Criteria convertToCriteria(Query query, Class<T> entityClass) {
        Criteria criteria = Criteria.empty();
        
        // 处理查询条件
        Condition[] conditions = query.getConditions();
        if (conditions != null && conditions.length > 0) {
            // 第一个条件
            Condition firstCondition = conditions[0];
            criteria = createCriteriaFromCondition(firstCondition, entityClass);
            
            // 处理剩余的条件，根据逻辑操作符进行组合
            for (int i = 1; i < conditions.length; i++) {
                Condition condition = conditions[i];
                Criteria conditionCriteria = createCriteriaFromCondition(condition, entityClass);
                Operator.Logical logical = condition.getLogical();
                criteria = switch (logical) {
                    case AND, NOT -> criteria.and(conditionCriteria);
                    case OR -> criteria.or(conditionCriteria);
                };
            }
        }
        
        return criteria;
    }
    
    /**
     * 根据条件创建 Criteria 对象
     * @param condition 查询条件
     * @param entityClass 实体类
     * @return Criteria 对象
     */
    private static <T> Criteria createCriteriaFromCondition(Condition condition, Class<T> entityClass) {
        String columnName = getColumnByFieldName(entityClass, condition.getField());
        Object value = condition.getValue();
        Operator.Comparison comparison = condition.getComparison();

        return switch (comparison) {
            case NEQ -> Criteria.where(columnName).not(value);
            case GT -> Criteria.where(columnName).greaterThan(value);
            case LT -> Criteria.where(columnName).lessThan(value);
            case GTE -> Criteria.where(columnName).greaterThanOrEquals(value);
            case LTE -> Criteria.where(columnName).lessThanOrEquals(value);
            case LIKE -> Criteria.where(columnName).like("%" + value + "%");
            case NOT_LIKE -> Criteria.where(columnName).notLike("%" + value + "%");
            case IN -> {
                if (value instanceof List) {
                    yield Criteria.where(columnName).in((List<?>) value);
                } else if (value instanceof Object[]) {
                    yield Criteria.where(columnName).in((Object[]) value);
                }
                yield Criteria.where(columnName).is(value);
            }
            case NOT_IN -> {
                if (value instanceof List) {
                    yield Criteria.where(columnName).notIn((List<?>) value);
                } else if (value instanceof Object[]) {
                    yield Criteria.where(columnName).notIn((Object[]) value);
                }
                yield Criteria.where(columnName).not(value);
            }
            case IS_NULL -> Criteria.where(columnName).isNull();
            case IS_NOT_NULL -> Criteria.where(columnName).isNotNull();
            case BETWEEN -> {
                if (value instanceof Object[] values && values.length >= 2) {
                    yield Criteria.where(columnName).between(values[0], values[1]);
                }
                yield Criteria.where(columnName).is(value);
            }
            case NOT_BETWEEN -> {
                if (value instanceof Object[] values && values.length >= 2) {
                    yield Criteria.where(columnName).notBetween(values[0], values[1]);
                }
                yield Criteria.where(columnName).not(value);
            }
            default -> Criteria.where(columnName).is(value);
        };
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