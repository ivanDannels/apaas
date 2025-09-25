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
package org.apaas.integration.service.impl;

import org.apaas.domain.service.impl.BaseServiceImpl;
import org.apaas.integration.entity.IntegrationEntity;
import org.apaas.integration.repository.IntegrationRepository;
import org.apaas.integration.service.IntegrationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IntegrationServiceImpl extends BaseServiceImpl<IntegrationEntity, Long, IntegrationRepository> implements IntegrationService {
    
    public IntegrationServiceImpl(IntegrationRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<IntegrationEntity> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Mono<IntegrationEntity> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public Mono<IntegrationEntity> save(IntegrationEntity entity) {
        return repository.save(entity);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}