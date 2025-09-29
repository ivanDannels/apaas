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
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.domain.domain.service.AbstractDomainService;
import org.apaas.system.domain.dto.NotificationDTO;
import org.apaas.system.entity.Notification;
import org.apaas.system.repository.NotificationRepository;
import org.apaas.system.service.ReactiveNotificationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 响应式通知服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveNotificationServiceImpl extends AbstractApplicationService<Notification, Long, NotificationRepository> implements ReactiveNotificationService {
    
    public ReactiveNotificationServiceImpl(NotificationRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<Notification> queryPage(NotificationDTO notificationDTO) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有通知
        return repository.findAll();
    }
    
    @Override
    public Mono<Notification> getDetail(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public Flux<Notification> getAllByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }
    
    @Override
    public Mono<Boolean> create(Notification notification) {
        return repository.save(notification).map(savedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> update(Notification notification) {
        return repository.save(notification).map(updatedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> delete(Long id) {
        return repository.deleteById(id).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> batchDelete(List<Long> ids) {
        return Flux.fromIterable(ids).flatMap(id -> repository.deleteById(id)).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> markAsRead(Long id) {
        return repository.findById(id).flatMap(notification -> {
            notification.setReadStatus(1);
            notification.setReadTime(LocalDateTime.now());
            return repository.save(notification);
        }).map(updatedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> batchMarkAsRead(List<Long> ids) {
        return Flux.fromIterable(ids).flatMap(id -> repository.findById(id)).flatMap(notification -> {
            notification.setReadStatus(1);
            notification.setReadTime(LocalDateTime.now());
            return repository.save(notification);
        }).then(Mono.just(true)).onErrorReturn(false);
    }
    
    @Override
    public Mono<Integer> countUnreadByUserId(Long userId) {
        // 这里需要根据实际需求实现查询逻辑
        // 暂时返回0
        return Mono.just(0);
    }
    
    @Override
    public Flux<Notification> getByUserId(Long userId, Integer pageNum, Integer pageSize) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有通知
        return repository.findAll();
    }
    
    @Override
    public Mono<Boolean> send(Notification notification) {
        notification.setSendStatus(1);
        notification.setSendTime(LocalDateTime.now());
        return repository.save(notification).map(savedNotification -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<Boolean> batchSend(List<Notification> notifications) {
        return Flux.fromIterable(notifications).doOnNext(notification -> {
            notification.setSendStatus(1);
            notification.setSendTime(LocalDateTime.now());
        }).flatMap(notification -> repository.save(notification)).then(Mono.just(true)).onErrorReturn(false);
    }
}