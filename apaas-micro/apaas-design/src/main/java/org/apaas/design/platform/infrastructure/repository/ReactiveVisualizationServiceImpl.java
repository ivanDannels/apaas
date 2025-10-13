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
import org.apaas.design.platform.domain.model.Visualization;
import org.apaas.design.platform.domain.repository.VisualizationRepository;
import org.apaas.design.platform.application.dto.VisualizationDTO;
import org.apaas.design.platform.application.assembler.VisualizationAssembler;
import org.apaas.design.platform.application.service.ReactiveVisualizationService;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.infrastructure.convert.PageConverter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 响应式数据可视化服务实现类
 * @author ivan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveVisualizationServiceImpl extends AbstractApplicationService<Visualization, Long, VisualizationRepository> implements ReactiveVisualizationService {
    
    private final VisualizationRepository repository;
    
    public ReactiveVisualizationServiceImpl(VisualizationRepository repository) {
        super(repository);
        this.repository = repository;
    }
    
    @Override
    public Mono<PageResult<VisualizationDTO>> selectPage(Query query) {
        Pageable pageable = PageConverter.convertPageable(query);
        return repository.findAll(pageable)
                .map(page -> {
                    PageResult<Visualization> pageResult = PageConverter.convertPageResult(page);
                    PageResult<VisualizationDTO> dtoPageResult = new PageResult<>();
                    dtoPageResult.setCurrent(pageResult.getCurrent());
                    dtoPageResult.setSize(pageResult.getSize());
                    dtoPageResult.setTotal(pageResult.getTotal());
                    dtoPageResult.setPages(pageResult.getPages());
                    dtoPageResult.setRecords(VisualizationAssembler.INSTANCE.convertEntityListToDtoList(pageResult.getRecords()));
                    return dtoPageResult;
                });
    }
    
    @Override
    public Mono<VisualizationDTO> create(VisualizationDTO visualizationDto) {
        Visualization visualization = VisualizationAssembler.INSTANCE.convertDtoToEntity(visualizationDto);
        return repository.save(visualization)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<VisualizationDTO> update(Long id, VisualizationDTO visualizationDto) {
        Visualization visualization = VisualizationAssembler.INSTANCE.convertDtoToEntity(visualizationDto);
        visualization.setId(id);
        return repository.save(visualization)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<VisualizationDTO> findById(Long id) {
        return repository.findById(id)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
    
    @Override
    public Mono<VisualizationDTO> save(VisualizationDTO visualizationDto) {
        Visualization visualization = VisualizationAssembler.INSTANCE.convertDtoToEntity(visualizationDto);
        return repository.save(visualization)
                .map(VisualizationAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<String> preview(Long id) {
        // TODO: 实现数据可视化预览逻辑
        return Mono.just("数据可视化预览成功");
    }
}