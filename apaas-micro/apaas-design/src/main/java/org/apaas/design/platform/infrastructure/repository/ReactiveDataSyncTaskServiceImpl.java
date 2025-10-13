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
package org.apaas.design.platform.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.application.service.AbstractApplicationService;
import org.apaas.design.platform.domain.model.DataSyncTask;
import org.apaas.design.platform.domain.repository.DataSyncTaskRepository;
import org.apaas.design.platform.application.dto.DataSyncTaskDTO;
import org.apaas.design.platform.application.assembler.DataSyncTaskAssembler;
import org.apaas.design.platform.application.service.ReactiveDataSyncTaskService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.infrastructure.convert.PageConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 响应式数据同步任务服务实现类
 * @author ivan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveDataSyncTaskServiceImpl extends AbstractApplicationService<DataSyncTask, Long, DataSyncTaskRepository> implements ReactiveDataSyncTaskService {
    
    private final DataSyncTaskRepository repository;
    
    public ReactiveDataSyncTaskServiceImpl(DataSyncTaskRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
    @Override
    public Mono<PageResult<DataSyncTaskDTO>> selectPage(Query query) {
        Pageable pageable = PageConverter.convertPageable(query);
        return repository.findAll(pageable)
                .map(page -> {
                    PageResult<DataSyncTask> pageResult = PageConverter.convertPageResult(page);
                    PageResult<DataSyncTaskDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(DataSyncTaskAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Override
    public Mono<DataSyncTaskDTO> create(DataSyncTaskDTO dataSyncTaskDto) {
        DataSyncTask dataSyncTask = DataSyncTaskAssembler.INSTANCE.convertDtoToEntity(dataSyncTaskDto);
        return repository.save(dataSyncTask)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<DataSyncTaskDTO> update(Long id, DataSyncTaskDTO dataSyncTaskDto) {
        DataSyncTask dataSyncTask = DataSyncTaskAssembler.INSTANCE.convertDtoToEntity(dataSyncTaskDto);
        dataSyncTask.setId(id);
        return repository.save(dataSyncTask)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<DataSyncTaskDTO> findById(Long id) {
        return repository.findById(id)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Mono<DataSyncTaskDTO> save(DataSyncTaskDTO dataSyncTaskDto) {
        DataSyncTask dataSyncTask = DataSyncTaskAssembler.INSTANCE.convertDtoToEntity(dataSyncTaskDto);
        return repository.save(dataSyncTask)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<String> execute(Long id) {
        // TODO: 实现数据同步执行逻辑
        return Mono.just("数据同步任务已提交");
    }
    
    @Override
    public Mono<String> pause(Long id) {
        // TODO: 实现暂停数据同步任务逻辑
        return Mono.just("数据同步任务已暂停");
    }
    
    @Override
    public Mono<String> resume(Long id) {
        // TODO: 实现恢复数据同步任务逻辑
        return Mono.just("数据同步任务已恢复");
    }
}