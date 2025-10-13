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
package org.apaas.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.application.dto.BaseDTO;
import org.apaas.application.service.ApplicationService;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;

/**
 * 响应式控制器基类
 * @author ivan
 * @param <D> DTO类型
 * @param <ID> 主键类型
 * @param <S> 服务类型
 */
@RequiredArgsConstructor
public abstract class BaseRest<D extends BaseDTO<ID>, ID extends Serializable, S extends ApplicationService<D, ID>> {
    
    protected final S service;
    
    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    public Mono<PageResult<D>> page(@RequestBody Query query) {
        return service.selectPage(query);
    }
    
    /**
     * 根据ID查询详情
     *
     * @param id 实体ID
     * @return DTO对象
     */
    @GetMapping("/{id}")
    public Mono<D> get(@PathVariable ID id) {
        return service.findById(id);
    }
    
    /**
     * 新增实体
     *
     * @param dto DTO对象
     * @return 保存结果
     */
    @PostMapping
    public Mono<D> add(@RequestBody D dto) {
        return service.save(dto);
    }
    
    /**
     * 批量新增实体
     *
     * @param dtoList DTO对象流
     * @return 保存结果
     */
    @PostMapping("/batch")
    public Flux<D> addBatch(@RequestBody Flux<D> dtoList) {
        return service.saveBatch(dtoList);
    }
    
    /**
     * 更新实体
     *
     * @param dto DTO对象
     * @return 更新结果
     */
    @PutMapping
    public Mono<D> update(@RequestBody D dto) {
        return service.save(dto);
    }
    
    /**
     * 批量更新实体
     *
     * @param dtoList DTO对象流
     * @return 更新结果
     */
    @PutMapping("/batch")
    public Flux<D> updateBatch(@RequestBody Flux<D> dtoList) {
        return service.updateBatch(dtoList);
    }
    
    /**
     * 删除实体
     *
     * @param id 实体ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable ID id) {
        return service.deleteById(id);
    }
    
    /**
     * 批量删除实体
     *
     * @param ids 实体ID列表
     * @return 删除结果
     */
    @Operation(summary = "批量删除数据")
    @Log(title = "删除数据", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/batch")
    public Mono<Void> deleteBatch(@Parameter(description = "ID集合") @RequestBody List<ID> ids) {
        return service.deleteAllById(ids);
    }
    
    /**
     * 导出实体
     *
     * @param query 查询条件
     * @return 导出结果
     */
    @PostMapping("/export")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入实体
     *
     * @param data 导入数据
     * @return 导入结果
     */
    @PostMapping("/import")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
    }
}