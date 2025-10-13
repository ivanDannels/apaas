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

import org.apaas.application.service.ApplicationService;
import org.apaas.system.application.dto.DataDictionaryDTO;
import org.apaas.system.domain.model.DataDictionaryAggregate;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式数据字典服务接口
 * @author ivan
 */
public interface ReactiveDataDictionaryService extends ApplicationService<DataDictionaryAggregate, Long> {
    
    /**
     * 分页查询数据字典
     */
    Flux<DataDictionaryDTO> selectPage(Pageable pageable, DataDictionaryDTO query);
    
    /**
     * 创建数据字典
     */
    Mono<Boolean> create(DataDictionaryDTO dictionary);
    
    /**
     * 更新数据字典
     */
    Mono<Boolean> update(DataDictionaryDTO dictionary);
    
    /**
     * 删除数据字典
     */
    Mono<Boolean> delete(Long id);
    
    /**
     * 启用/停用数据字典
     */
    Mono<Boolean> changeStatus(Long id, Integer status);
    
    /**
     * 导出数据字典
     */
    Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query);
    
    /**
     * 导入数据字典
     */
    Mono<Boolean> importExcel(byte[] fileData);
    
    /**
     * 根据ID查询数据字典
     */
    Mono<DataDictionaryDTO> findById(Long id);
}