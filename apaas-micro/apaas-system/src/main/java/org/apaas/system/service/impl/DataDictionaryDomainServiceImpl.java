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
package org.apaas.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.service.domain.AbstractDomainService;
import org.apaas.domain.specification.Specification;
import org.apaas.system.entity.DataDictionaryAggregate;
import org.apaas.system.repository.DataDictionaryAggregateRepository;
import org.apaas.system.service.DataDictionaryDomainService;
import org.apaas.system.specification.DataDictionarySpecification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 数据字典领域服务实现
 * 处理数据字典相关的复杂业务逻辑
 *
 * @author ivan
 */
@Slf4j
@Service
public class DataDictionaryDomainServiceImpl extends AbstractDomainService<DataDictionaryAggregate, Long, DataDictionaryAggregateRepository> 
        implements DataDictionaryDomainService {
    
    public DataDictionaryDomainServiceImpl(DataDictionaryAggregateRepository repository) {
        super(repository);
    }
    
    /**
     * 创建数据字典
     *
     * @param dictionary 数据字典聚合根
     * @return 创建后的数据字典
     */
    @Override
    public Mono<DataDictionaryAggregate> createDictionary(DataDictionaryAggregate dictionary) {
        log.info("创建数据字典: name={}", dictionary.getName());
        
        // 定义创建数据字典的业务规范
        Specification<DataDictionaryAggregate> createSpec = 
                DataDictionarySpecification.nonEmptyName()
                        .and(DataDictionarySpecification.validCodeFormat())
                        .and(DataDictionarySpecification.validType());
        
        // 验证并保存
        return validateAndSave(dictionary, createSpec)
                .doOnSuccess(saved -> log.info("数据字典创建成功: id={}, name={}", saved.getId(), saved.getName()))
                .doOnError(error -> log.error("数据字典创建失败: name={}, error={}", dictionary.getName(), error.getMessage()));
    }
    
    /**
     * 更新数据字典
     *
     * @param dictionary 数据字典聚合根
     * @return 更新后的数据字典
     */
    @Override
    public Mono<DataDictionaryAggregate> updateDictionary(DataDictionaryAggregate dictionary) {
        log.info("更新数据字典: id={}, name={}", dictionary.getId(), dictionary.getName());
        
        // 定义更新数据字典的业务规范
        Specification<DataDictionaryAggregate> updateSpec = 
                DataDictionarySpecification.nonEmptyName()
                        .and(DataDictionarySpecification.validCodeFormat())
                        .and(DataDictionarySpecification.validType())
                        .and(DataDictionarySpecification.validStatus());
        
        // 验证并保存
        return validateAndSave(dictionary, updateSpec)
                .doOnSuccess(saved -> log.info("数据字典更新成功: id={}, name={}", saved.getId(), saved.getName()))
                .doOnError(error -> log.error("数据字典更新失败: id={}, error={}", dictionary.getId(), error.getMessage()));
    }
    
    /**
     * 删除数据字典
     *
     * @param dictionaryId 数据字典ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteDictionary(Long dictionaryId) {
        log.info("删除数据字典: id={}", dictionaryId);
        
        // 定义删除数据字典的业务规范（系统字典不能删除）
        Specification<DataDictionaryAggregate> deleteSpec = DataDictionarySpecification.notSystemDictionary();
        
        return repository.findById(dictionaryId)
                .switchIfEmpty(Mono.error(new RuntimeException("数据字典不存在")))
                .flatMap(dictionary -> checkSpecification(dictionary, deleteSpec)
                        .flatMap(satisfied -> {
                            if (!satisfied) {
                                return Mono.error(new RuntimeException("系统字典不能删除"));
                            }
                            return repository.deleteById(dictionaryId).thenReturn(true);
                        }))
                .doOnSuccess(result -> log.info("数据字典删除成功: id={}", dictionaryId))
                .doOnError(error -> log.error("数据字典删除失败: id={}, error={}", dictionaryId, error.getMessage()));
    }
    
    /**
     * 启用/停用数据字典
     *
     * @param dictionaryId 数据字典ID
     * @param status 状态：0-正常，1-停用
     * @return 更新后的数据字典
     */
    @Override
    public Mono<DataDictionaryAggregate> changeStatus(Long dictionaryId, Integer status) {
        log.info("修改数据字典状态: id={}, status={}", dictionaryId, status);
        
        return repository.findById(dictionaryId)
                .switchIfEmpty(Mono.error(new RuntimeException("数据字典不存在")))
                .flatMap(dictionary -> {
                    dictionary.setStatus(status);
                    return repository.save(dictionary);
                })
                .doOnSuccess(updated -> {
                    String statusDesc = status == 0 ? "启用" : "禁用";
                    log.info("数据字典状态修改成功: id={}, name={}, status={}", updated.getId(), updated.getName(), statusDesc);
                })
                .doOnError(error -> log.error("数据字典状态修改失败: id={}, error={}", dictionaryId, error.getMessage()));
    }
    
    /**
     * 执行领域逻辑
     *
     * @param domainObject 领域对象
     * @return 处理结果
     */
    @Override
    public Mono<DataDictionaryAggregate> execute(DataDictionaryAggregate domainObject) {
        // 默认实现，保存数据字典
        return repository.save(domainObject);
    }
}