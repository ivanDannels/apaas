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
package org.apaas.design.platform.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.design.platform.domain.model.DataSyncTask;
import org.apaas.design.platform.application.dto.DataSyncTaskDTO;

import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * 响应式数据同步任务服务接口
 * @author ivan
 */
public interface ReactiveDataSyncTaskService extends ApplicationService<DataSyncTask, Long> {
    
    /**
     * 分页查询数据同步任务
     */
    Mono<PageResult<DataSyncTaskDTO>> selectPage(Query query);
    
    /**
     * 创建数据同步任务
     */
    Mono<DataSyncTaskDTO> create(DataSyncTaskDTO dataSyncTaskDto);
    
    /**
     * 更新数据同步任务
     */
    Mono<DataSyncTaskDTO> update(Long id, DataSyncTaskDTO dataSyncTaskDto);
    
    /**
     * 根据ID获取数据同步任务
     */
    Mono<DataSyncTaskDTO> findById(Long id);
    
    /**
     * 根据ID删除数据同步任务
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * 保存数据同步任务
     */
    Mono<DataSyncTaskDTO> save(DataSyncTaskDTO dataSyncTaskDto);
    
    /**
     * 执行数据同步任务
     */
    Mono<String> execute(Long id);
    
    /**
     * 暂停数据同步任务
     */
    Mono<String> pause(Long id);
    
    /**
     * 恢复数据同步任务
     */
    Mono<String> resume(Long id);
}