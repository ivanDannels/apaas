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
package org.apaas.domain.factory;

import java.io.Serializable;

/**
 * 实体工厂接口
 * 用于创建复杂的实体对象，封装对象创建逻辑
 *
 * @author ivan
 * @param <T> 实体类型
 * @param <ID> 实体标识类型
 */
public interface EntityFactory<T, ID extends Serializable> {
    
    /**
     * 创建实体对象
     *
     * @param id 实体标识
     * @param args 创建参数
     * @return 实体对象
     */
    T create(ID id, Object... args);
    
    /**
     * 创建实体对象（无参）
     *
     * @return 实体对象
     */
    T create();
    
    /**
     * 从原型创建实体对象
     *
     * @param prototype 原型对象
     * @return 实体对象
     */
    T createFrom(T prototype);
}