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
package org.apaas.system.service;

import org.apaas.domain.service.domain.DomainService;
import org.apaas.system.entity.DataDictionaryAggregate;
import reactor.core.publisher.Mono;

/**
 * 数据字典领域服务接口
 * 处理数据字典相关的复杂业务逻辑
 *
 * @author ivan
 */
public interface DataDictionaryDomainService extends DomainService<DataDictionaryAggregate> {
    
    /**
     * 创建数据字典
     *
     * @param dictionary 数据字典聚合根
     * @return 创建后的数据字典
     */
    Mono<DataDictionaryAggregate> createDictionary(DataDictionaryAggregate dictionary);
    
    /**
     * 更新数据字典
     *
     * @param dictionary 数据字典聚合根
     * @return 更新后的数据字典
     */
    Mono<DataDictionaryAggregate> updateDictionary(DataDictionaryAggregate dictionary);
    
    /**
     * 删除数据字典
     *
     * @param dictionaryId 数据字典ID
     * @return 删除结果
     */
    Mono<Boolean> deleteDictionary(Long dictionaryId);
    
    /**
     * 启用/停用数据字典
     *
     * @param dictionaryId 数据字典ID
     * @param status 状态：0-正常，1-停用
     * @return 更新后的数据字典
     */
    Mono<DataDictionaryAggregate> changeStatus(Long dictionaryId, Integer status);
}