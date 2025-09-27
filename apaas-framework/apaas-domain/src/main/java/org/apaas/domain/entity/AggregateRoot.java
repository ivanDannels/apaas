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

/**
 * 聚合根标记接口
 * 聚合根是聚合的入口点，负责维护聚合内部的一致性
 * 聚合根具有全局唯一标识，是唯一可以被外部直接访问的实体
 *
 * @author ivan
 * @param <ID> 聚合根标识类型
 */
public interface AggregateRoot<ID extends Serializable> {
    
    /**
     * 获取聚合根标识
     *
     * @return 聚合根标识
     */
    ID getId();
    
    /**
     * 设置聚合根标识
     *
     * @param id 聚合根标识
     */
    void setId(ID id);
}