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
package org.apaas.domain.service.application;

import org.apaas.domain.entity.Order;
import org.apaas.domain.repository.OrderRepository;
import org.apaas.domain.service.application.AbstractApplicationService;
import org.springframework.stereotype.Service;

/**
 * 订单应用服务
 * 处理订单相关的应用层逻辑
 *
 * @author ivan
 */
@Service
public class OrderApplicationService extends AbstractApplicationService<Order, Long, OrderRepository> {
    
    private final OrderDomainService orderDomainService;
    
    public OrderApplicationService(OrderRepository repository, OrderDomainService orderDomainService) {
        super(repository);
        this.orderDomainService = orderDomainService;
    }
    
    /**
     * 创建订单
     *
     * @param order 订单
     * @return 创建后的订单
     */
    public reactor.core.publisher.Mono<Order> createOrder(Order order) {
        return orderDomainService.createOrder(order);
    }
    
    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @return 取消后的订单
     */
    public reactor.core.publisher.Mono<Order> cancelOrder(Long orderId) {
        return orderDomainService.cancelOrder(orderId);
    }
}