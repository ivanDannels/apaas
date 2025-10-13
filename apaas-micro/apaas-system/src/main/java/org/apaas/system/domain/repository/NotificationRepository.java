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
package org.apaas.system.domain.repository;

import org.apaas.system.domain.model.Notification;
import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 通知Repository接口
 */
@Repository
public interface NotificationRepository extends ReactiveBaseRepository<Notification, Long> {
    
    /**
     * 根据用户ID查询通知列表
     *
     * @param userId 用户ID
     * @return 通知列表
     */
    Flux<Notification> findByReceiverId(Long userId);
    
    /**
     * 根据用户ID和阅读状态查询通知列表
     *
     * @param userId 用户ID
     * @param readStatus 阅读状态
     * @return 通知列表
     */
    Flux<Notification> findByReceiverIdAndReadStatus(Long userId, Integer readStatus);
    
    /**
     * 根据用户ID查询未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Mono<Long> countByReceiverIdAndReadStatus(Long userId, Integer readStatus);
}