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
package org.apaas.domain.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 值对象基类
 * 值对象是没有唯一标识的对象，通过属性值来判断相等性
 * 值对象应该是不可变的
 *
 * @author ivan
 */
public abstract class BaseValueObject implements Serializable {
    
    /**
     * 值对象相等性比较
     * 值对象通过属性值来判断相等性，而不是通过标识
     *
     * @param other 另一个对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        BaseValueObject that = (BaseValueObject) other;
        return equalsImpl(that);
    }
    
    /**
     * 子类实现具体的相等性比较逻辑
     *
     * @param other 另一个值对象
     * @return 是否相等
     */
    protected abstract boolean equalsImpl(BaseValueObject other);
    
    /**
     * 值对象哈希码计算
     *
     * @return 哈希码
     */
    @Override
    public abstract int hashCode();
    
    /**
     * 值对象字符串表示
     *
     * @return 字符串表示
     */
    @Override
    public abstract String toString();
}