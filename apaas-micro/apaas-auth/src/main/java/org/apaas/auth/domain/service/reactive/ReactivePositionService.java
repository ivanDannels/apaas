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
package org.apaas.auth.domain.service.reactive;

import org.apaas.auth.domain.entity.Position;
import org.apaas.domain.application.service.ApplicationService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
public interface ReactivePositionService extends ApplicationService<Position, Long> {
    
    /**
     * 根据用户ID获取岗位列表
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    Flux<Position> getPositionsByUserId(Long userId);
    
    /**
     * 为用户分配岗位
     *
     * @param userId     用户ID
     * @param positionId 岗位ID
     * @return 是否成功
     */
    Mono<Boolean> assignPositionToUser(Long userId, Long positionId);
    
    /**
     * 移除用户的岗位
     *
     * @param userId     用户ID
     * @param positionId 岗位ID
     * @return 是否成功
     */
    Mono<Boolean> removePositionFromUser(Long userId, Long positionId);
}
