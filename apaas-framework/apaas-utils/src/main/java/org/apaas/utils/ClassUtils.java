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
package org.apaas.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 类工具类
 * @author ivan
 */
@UtilityClass
public class ClassUtils {
    
    /**
     * 获取指定位置的泛型参数类型
     * @param clazz 需要获取泛型类型的类
     * @param index 泛型参数位置索引（从0开始）
     * @return 指定位置的泛型参数类型，如果无法获取则返回null
     */
    public <T> Class<T> getGenericType(Class<?> clazz, int index) {
        Class<?>[] genericTypes = getAllGenericTypes(clazz);
        return index >= 0 && index < genericTypes.length ? (Class<T>) genericTypes[index] : null;
    }
    
    /**
     * 获取所有泛型参数类型
     * @param clazz 需要获取泛型类型的类
     * @return 泛型参数类型数组，如果没有泛型参数则返回空数组
     */
    public Class<?>[] getAllGenericTypes(Class<?> clazz) {
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            return Arrays.stream(parameterizedType.getActualTypeArguments()).map(type -> {
                if (type instanceof Class) {
                    return (Class<?>) type;
                } else if (type instanceof ParameterizedType) {
                    return (Class<?>) ((ParameterizedType) type).getRawType();
                }
                return null;
            }).toArray(Class<?>[]::new);
        }
        return new Class<?>[0];
    }
    
}
