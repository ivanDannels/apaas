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
package org.apaas.domain.domain.specification;

import java.io.Serializable;

/**
 * 规范接口
 * 用于封装业务规则，支持规则的组合和复用
 *
 * @author ivan
 * @param <T> 检查对象类型
 */
public interface Specification<T> extends Serializable {
    
    /**
     * 检查对象是否满足规范
     *
     * @param candidate 检查对象
     * @return 是否满足规范
     */
    boolean isSatisfiedBy(T candidate);
    
    /**
     * 与另一个规范组合（AND）
     *
     * @param other 另一个规范
     * @return 组合后的规范
     */
    default Specification<T> and(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate);
    }
    
    /**
     * 或另一个规范组合（OR）
     *
     * @param other 另一个规范
     * @return 组合后的规范
     */
    default Specification<T> or(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate) || other.isSatisfiedBy(candidate);
    }
    
    /**
     * 非操作（NOT）
     *
     * @return 取反后的规范
     */
    default Specification<T> not() {
        return candidate -> !this.isSatisfiedBy(candidate);
    }
}