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
import java.util.Objects;

/**
 * SQL排序对象
 *
 * @author ivan
 */
@Data
public class Sorter implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 5534075845325254075L;
    
    /**
     * 排序的字段
     */
    private String field;
    
    /**
     * 排序方式（正序还是反序）
     */
    private Direction direction;
    
    public Sorter() {
    }
    
    /**
     * 构造函数
     *
     * @param field     字段
     * @param direction 排序方式
     */
    public Sorter(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }
    
    /**
     * 创建一个升序排序器
     *
     * @param field 需要排序的字段
     * @return 返回创建的排序器对象
     */
    public static Sorter asc(String field) {
        return new Sorter(field, Direction.ASC);
    }
    
    /**
     * 创建一个降序排序器
     *
     * @param field 需要排序的字段
     * @return 返回创建的排序器对象
     */
    public static Sorter desc(String field) {
        return new Sorter(field, Direction.DESC);
    }
    
    /**
     * 判断是否是正序
     * @return boolean
     */
    public boolean isAscending() {
        return direction == Direction.ASC;
    }
    
    /**
     * 判断是否是反序
     * @return boolean
     */
    public boolean isDescending() {
        return direction == Direction.DESC;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sorter sorter = (Sorter) o;
        return field.equals(sorter.field) && direction == sorter.direction;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(field, direction);
    }
    
}
