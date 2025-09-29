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
package org.apaas.domain.domain.repository;

import org.apaas.domain.domain.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 订单仓库接口
 *
 * @author ivan
 */
public interface OrderRepository extends ReactiveBaseRepository<Order, Long> {
    
    /**
     * 根据客户ID查找订单
     *
     * @param customerId 客户ID
     * @return 订单列表
     */
    Flux<Order> findByCustomerId(Long customerId);
    
    /**
     * 根据客户ID分页查找订单
     *
     * @param customerId 客户ID
     * @param pageable 分页参数
     * @return 订单列表
     */
    Flux<Order> findByCustomerId(Long customerId, Pageable pageable);
    
    /**
     * 根据订单号查找订单
     *
     * @param orderNo 订单号
     * @return 订单
     */
    Mono<Order> findByOrderNo(String orderNo);
    
    /**
     * 根据订单状态查找订单数量
     *
     * @param status 订单状态
     * @return 订单数量
     */
    Mono<Long> countByStatus(String status);
}