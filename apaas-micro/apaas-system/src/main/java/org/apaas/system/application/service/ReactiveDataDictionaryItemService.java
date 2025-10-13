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
package org.apaas.system.application.service;

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.system.domain.model.DataDictionaryItem;
import org.apaas.system.application.dto.DataDictionaryItemDTO;
import org.apaas.core.query.Query;
import org.apaas.core.query.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式数据字典项服务接口
 * @author ivan
 */
public interface ReactiveDataDictionaryItemService extends ApplicationService<DataDictionaryItem, Long> {
    
    /**
     * 分页查询数据字典项
     */
    Mono<PageResult<DataDictionaryItemDTO>> selectPage(Query query);
    
    /**
     * 根据字典ID查询字典项列表
     */
    Flux<DataDictionaryItemDTO> selectByDictionaryId(Long dictionaryId);
    
    /**
     * 创建数据字典项
     */
    Mono<Boolean> create(DataDictionaryItemDTO dataDictionaryItemDto);
    
    /**
     * 更新数据字典项
     */
    Mono<Boolean> update(DataDictionaryItemDTO dataDictionaryItemDto);
    
    /**
     * 删除数据字典项
     */
    Mono<Boolean> delete(Long id);
    
    /**
     * 启用/停用数据字典项
     */
    Mono<Boolean> changeStatus(Long id, Integer status);
    
    /**
     * 导出数据字典项
     */
    Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryItemDTO query);
    
    /**
     * 导入数据字典项
     */
    Mono<Boolean> importExcel(Long dictionaryId, byte[] fileData);
    
    /**
     * 根据ID查询数据字典项
     */
    Mono<DataDictionaryItemDTO> findById(Long id);
    
    /**
     * 保存批量数据字典项
     */
    Flux<DataDictionaryItemDTO> saveBatch(Flux<DataDictionaryItemDTO> items);
    
    /**
     * 更新批量数据字典项
     */
    Flux<DataDictionaryItemDTO> updateBatch(Flux<DataDictionaryItemDTO> items);
}