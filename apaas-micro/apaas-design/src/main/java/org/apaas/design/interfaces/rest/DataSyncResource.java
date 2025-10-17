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
package org.apaas.design.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.design.application.service.ReactiveDataSyncTaskService;
import org.apaas.design.domain.model.DataSyncTask;
import org.apaas.design.application.dto.DataSyncTaskDTO;
import org.apaas.design.application.assembler.DataSyncTaskAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 数据同步资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/data-sync-tasks")
@Tag(name = "数据同步", description = "数据同步任务管理接口")
@RequiredArgsConstructor
public class DataSyncResource {
    
    private final ReactiveDataSyncTaskService dataSyncTaskService;
    
    @Operation(summary = "创建数据同步任务")
    @PostMapping
    public Mono<DataSyncTaskDTO> create(@RequestBody DataSyncTaskDTO dataSyncTaskDto) {
        DataSyncTask dataSyncTask = DataSyncTaskAssembler.INSTANCE.convertDtoToEntity(dataSyncTaskDto);
        return dataSyncTaskService.save(dataSyncTask)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取数据同步任务")
    @GetMapping("/{id}")
    public Mono<DataSyncTaskDTO> getById(@PathVariable Long id) {
        return dataSyncTaskService.findById(id)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新数据同步任务")
    @PutMapping("/{id}")
    public Mono<DataSyncTaskDTO> update(@PathVariable Long id, @RequestBody DataSyncTaskDTO dataSyncTaskDto) {
        DataSyncTask dataSyncTask = DataSyncTaskAssembler.INSTANCE.convertDtoToEntity(dataSyncTaskDto);
        dataSyncTask.setId(id);
        return dataSyncTaskService.save(dataSyncTask)
                .map(DataSyncTaskAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除数据同步任务")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return dataSyncTaskService.deleteById(id);
    }
    
    @Operation(summary = "分页查询数据同步任务")
    @PostMapping("/page")
    public Mono<PageResult<DataSyncTaskDTO>> page(@RequestBody Query query) {
        return dataSyncTaskService.selectPage(query)
                .map(pageResult -> {
                    PageResult<DataSyncTaskDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(DataSyncTaskAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Operation(summary = "手动执行数据同步任务")
    @PostMapping("/{id}/execute")
    public Mono<String> execute(@PathVariable Long id) {
        // TODO: 实现数据同步执行逻辑
        return Mono.just("数据同步任务已提交");
    }
    
    @Operation(summary = "暂停数据同步任务")
    @PostMapping("/{id}/pause")
    public Mono<String> pause(@PathVariable Long id) {
        // TODO: 实现暂停数据同步任务逻辑
        return Mono.just("数据同步任务已暂停");
    }
    
    @Operation(summary = "恢复数据同步任务")
    @PostMapping("/{id}/resume")
    public Mono<String> resume(@PathVariable Long id) {
        // TODO: 实现恢复数据同步任务逻辑
        return Mono.just("数据同步任务已恢复");
    }
}