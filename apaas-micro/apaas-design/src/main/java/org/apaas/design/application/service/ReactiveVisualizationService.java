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
package org.apaas.design.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.design.domain.model.Visualization;
import org.apaas.design.application.dto.VisualizationDTO;

import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * 响应式数据可视化服务接口
 * @author ivan
 */
public interface ReactiveVisualizationService extends ApplicationService<Visualization, Long> {
    
    /**
     * 分页查询数据可视化配置
     */
    Mono<PageResult<VisualizationDTO>> selectPage(Query query);
    
    /**
     * 创建数据可视化配置
     */
    Mono<VisualizationDTO> create(VisualizationDTO visualizationDto);
    
    /**
     * 更新数据可视化配置
     */
    Mono<VisualizationDTO> update(Long id, VisualizationDTO visualizationDto);
    
    /**
     * 根据ID获取数据可视化配置
     */
    Mono<VisualizationDTO> findById(Long id);
    
    /**
     * 根据ID删除数据可视化配置
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * 保存数据可视化配置
     */
    Mono<VisualizationDTO> save(VisualizationDTO visualizationDto);
    
    /**
     * 预览数据可视化图表
     */
    Mono<String> preview(Long id);
}