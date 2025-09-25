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
package org.apaas.form.engine.service.reactive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.context.TenantContext;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.apaas.form.engine.entity.FormDefinition;
import org.apaas.form.engine.repository.FormDefinitionRepository;
import org.apaas.form.engine.service.reactive.ReactiveFormDefinitionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 响应式表单定义服务实现类
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveFormDefinitionServiceImpl extends BaseServiceImpl<FormDefinition, Long, FormDefinitionRepository> implements ReactiveFormDefinitionService {
    
    public ReactiveFormDefinitionServiceImpl(FormDefinitionRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<Long> saveFormDefinition(FormDefinition formDefinition) {
        // 设置创建时间、更新时间
        LocalDateTime now = LocalDateTime.now();
        formDefinition.setCreatedTime(now);
        formDefinition.setUpdatedTime(now);
        // 初始状态为草稿
        formDefinition.setStatus(0);
        return handleVersion(formDefinition).flatMap(this::save).map(FormDefinition::getId);
    }
    
    @Override
    public Mono<Boolean> updateFormDefinition(FormDefinition formDefinition) {
        return findById(formDefinition.getId()).flatMap(oldDefinition -> {
            if (oldDefinition.getStatus() == 1) {
                return Mono.error(new RuntimeException("已发布的表单定义不允许修改"));
            }
            return save(formDefinition).map(saved -> true);
        });
    }
    
    @Override
    public Mono<Boolean> deleteFormDefinitions(Long[] ids) {
        // 检查是否有已发布的表单定义
        return Flux.fromArray(ids).flatMap(this::findById).collectList().flatMap(list -> {
            boolean hasPublished = list.stream().anyMatch(fd -> fd.getStatus() == 1);
            if (hasPublished) {
                return Mono.error(new RuntimeException("包含已发布的表单定义，不允许删除"));
            }
            
            return Flux.fromArray(ids).flatMap(this::deleteById).then(Mono.just(true));
        });
    }
    
    @Override
    public Mono<Boolean> publishFormDefinition(Long id) {
        return findById(id).switchIfEmpty(Mono.error(new RuntimeException("表单定义不存在"))).flatMap(formDefinition -> {
            // 设置状态为已发布
            formDefinition.setStatus(1);
            formDefinition.setUpdatedTime(LocalDateTime.now());
            // 如果设为默认版本，则更新其他版本为非默认
            Mono<FormDefinition> updateMono = Mono.just(formDefinition);
            if (formDefinition.getIsDefault()) {
                updateMono = repository.updateIsDefaultByCode(formDefinition.getCode(), false).then(Mono.just(formDefinition));
            }
            
            return updateMono.flatMap(this::save).map(saved -> true);
        });
    }
    
    @Override
    public Mono<Boolean> disableFormDefinition(Long id) {
        return findById(id).switchIfEmpty(Mono.error(new RuntimeException("表单定义不存在"))).flatMap(formDefinition -> {
            // 设置状态为已停用
            formDefinition.setStatus(2);
            formDefinition.setUpdatedTime(LocalDateTime.now());
            
            return save(formDefinition).map(saved -> true);
        });
    }
    
    @Override
    public Flux<FormDefinition> getVersionsByCode(String code) {
        return repository.findByCode(code).sort((f1, f2) -> f2.getVersion().compareTo(f1.getVersion()));
    }
    
    @Override
    public Mono<Long> copyFormDefinition(Long id, String newName) {
        return findById(id).switchIfEmpty(Mono.error(new RuntimeException("表单定义不存在"))).flatMap(source -> {
            // 创建新的表单定义
            FormDefinition newDefinition = FormDefinition.builder().build();
            newDefinition.setName(newName);
            newDefinition.setCode(source.getCode());
            newDefinition.setType(source.getType());
            newDefinition.setConfigJson(source.getConfigJson());
            newDefinition.setItemsJson(source.getItemsJson());
            newDefinition.setDataSourceId(source.getDataSourceId());
            newDefinition.setFlowId(source.getFlowId());
            newDefinition.setStatus(0); // 草稿状态
            newDefinition.setIsDefault(false);
            // 处理版本号
            return handleVersion(newDefinition).flatMap(this::save).map(FormDefinition::getId);
        });
    }
    
    @Override
    public Mono<byte[]> exportFormDefinition(Long id) {
        return findById(id).switchIfEmpty(Mono.error(new RuntimeException("表单定义不存在"))).map(formDefinition -> formDefinition.getItemsJson().getBytes());
    }
    
    @Override
    public Mono<Long> importFormDefinition(byte[] data) {
        String itemsJson = new String(data);
        // 实际项目中应解析JSON并创建表单定义
        FormDefinition formDefinition = FormDefinition.builder().build();
        formDefinition.setName("导入的表单");
        formDefinition.setCode("IMPORT_" + System.currentTimeMillis());
        formDefinition.setItemsJson(itemsJson);
        formDefinition.setStatus(0);
        
        return handleVersion(formDefinition).flatMap(this::save).map(FormDefinition::getId);
    }
    
    /**
     * 处理表单版本号
     *
     * @param formDefinition 表单定义
     * @return 处理后的表单定义
     */
    private Mono<FormDefinition> handleVersion(FormDefinition formDefinition) {
        return repository.findByCode(formDefinition.getCode()).collectList().map(list -> {
            if (list.isEmpty()) {
                // 新表单，版本号为1
                formDefinition.setVersion(1);
                formDefinition.setIsDefault(true);
            } else {
                // 已有表单，版本号+1
                int maxVersion = list.stream().mapToInt(FormDefinition::getVersion).max().orElse(0);
                formDefinition.setVersion(maxVersion + 1);
                formDefinition.setIsDefault(false);
            }
            return formDefinition;
        });
    }
}