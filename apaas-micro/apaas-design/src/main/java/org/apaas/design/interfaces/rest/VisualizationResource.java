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
import org.apaas.design.application.service.ReactiveVisualizationService;
import org.apaas.design.domain.model.Visualization;
import org.apaas.design.application.dto.VisualizationDTO;
import org.apaas.design.application.assembler.VisualizationAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 数据可视化资源控制器
 *
 * @author ivan
 */
@Slf4j
@RestController
@RequestMapping("/visualizations")
@Tag(name = "数据可视化", description = "数据可视化配置接口")
@RequiredArgsConstructor
public class VisualizationResource {
    
    private final ReactiveVisualizationService visualizationService;
    
    @Operation(summary = "创建数据可视化配置")
    @PostMapping
    public Mono<VisualizationDTO> create(@RequestBody VisualizationDTO visualizationDto) {
        Visualization visualization = VisualizationAssembler.INSTANCE.convertDtoToEntity(visualizationDto);
        return visualizationService.save(visualization)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "根据ID获取数据可视化配置")
    @GetMapping("/{id}")
    public Mono<VisualizationDTO> getById(@PathVariable Long id) {
        return visualizationService.findById(id)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "更新数据可视化配置")
    @PutMapping("/{id}")
    public Mono<VisualizationDTO> update(@PathVariable Long id, @RequestBody VisualizationDTO visualizationDto) {
        Visualization visualization = VisualizationAssembler.INSTANCE.convertDtoToEntity(visualizationDto);
        visualization.setId(id);
        return visualizationService.save(visualization)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Operation(summary = "删除数据可视化配置")
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return visualizationService.deleteById(id);
    }
    
    @Operation(summary = "分页查询数据可视化配置")
    @PostMapping("/page")
    public Mono<PageResult<VisualizationDTO>> page(@RequestBody Query query) {
        return visualizationService.selectPage(query)
                .map(pageResult -> {
                    PageResult<VisualizationDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(VisualizationAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Operation(summary = "预览数据可视化图表")
    @PostMapping("/{id}/preview")
    public Mono<String> preview(@PathVariable Long id) {
        // TODO: 实现数据可视化预览逻辑
        return Mono.just("数据可视化预览成功");
    }
}