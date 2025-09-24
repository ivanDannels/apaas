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

/**
 * 数组工具类，提供一系列操作数组的实用方法。
 */
public class ArrayUtils {
    
    /**
     * 检查数组是否为空。
     * @param array 待检查的数组
     * @param <T> 数组元素的类型
     * @return 如果数组为 null 或长度为 0，返回 true；否则返回 false
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
    
    /**
     * 检查数组是否不为空。
     * @param array 待检查的数组
     * @param <T> 数组元素的类型
     * @return 如果数组不为 null 且长度大于 0，返回 true；否则返回 false
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }
    
    /**
     * 获取数组的长度，如果数组为 null 则返回 0。
     * @param array 待检查的数组
     * @param <T> 数组元素的类型
     * @return 数组的长度，如果数组为 null 则返回 0
     */
    public static <T> int length(T[] array) {
        return array == null ? 0 : array.length;
    }
    
    /**
     * 将多个数组合并为一个数组。
     * @param arrays 待合并的多个数组
     * @param <T> 数组元素的类型
     * @return 合并后的新数组，如果所有输入数组都为 null 或空，则返回空数组
     */
    @SafeVarargs
    public static <T> T[] addAll(T[]... arrays) {
        if (isEmpty(arrays)) {
            return (T[]) new Object[0];
        }
        
        int totalLength = 0;
        for (T[] array : arrays) {
            totalLength += length(array);
        }
        
        try {
            @SuppressWarnings("unchecked")
            T[] result = (T[]) java.lang.reflect.Array.newInstance(arrays[0].getClass().getComponentType(), totalLength);
            int offset = 0;
            for (T[] array : arrays) {
                if (isNotEmpty(array)) {
                    System.arraycopy(array, 0, result, offset, array.length);
                    offset += array.length;
                }
            }
            return result;
        } catch (NullPointerException e) {
            return (T[]) new Object[0];
        }
    }
}
