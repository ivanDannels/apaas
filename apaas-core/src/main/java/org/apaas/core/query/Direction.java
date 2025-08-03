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
 * 排序方式（升序或者降序）
 *
 * @author ivan
 */
public enum Direction {
    
    /**
     * 升序
     */
    ASC,
    
    /**
     * 降序
     */
    DESC;
    
    /**
     * 根据字符串值返回对应{@link Direction}值
     *
     * @param value 排序方式字符串，只能是 ASC或DESC
     * @return {@link Direction}
     * @throws IllegalArgumentException 如果给定值无法解析为枚举值。
     */
    public static Direction fromString(String value) throws IllegalArgumentException {
        try {
            return Direction.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Invalid value [%s] for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
        }
    }
    
}
