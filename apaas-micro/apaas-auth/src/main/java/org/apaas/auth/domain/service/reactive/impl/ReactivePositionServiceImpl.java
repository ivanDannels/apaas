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
package org.apaas.auth.domain.service.reactive.impl;

import org.apaas.auth.domain.entity.Position;
import org.apaas.auth.domain.repository.reactive.ReactivePositionRepository;
import org.apaas.auth.domain.service.reactive.ReactivePositionService;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
@Service
public class ReactivePositionServiceImpl extends AbstractApplicationService<Position, Long, ReactivePositionRepository> implements ReactivePositionService {
    
    public ReactivePositionServiceImpl(ReactivePositionRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<Position> getPositionsByUserId(Long userId) {
        // 实现根据用户ID获取岗位列表的逻辑
        return repository.findByIdAndDeletedFalse(userId).flux();
    }
    
    @Override
    public Mono<Boolean> assignPositionToUser(Long userId, Long positionId) {
        // 实现为用户分配岗位的逻辑
        return Mono.fromCallable(() -> {
            // 这里应该实现具体的业务逻辑
            // 例如在用户岗位关联表中插入一条记��?
            return true;
        });
    }
    
    @Override
    public Mono<Boolean> removePositionFromUser(Long userId, Long positionId) {
        // 实现移除用户岗位的逻辑
        return Mono.fromCallable(() -> {
            // 这里应该实现具体的业务逻辑
            // 例如从用户岗位关联表中删除一条记��?
            return true;
        });
    }
}
