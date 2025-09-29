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
package org.apaas.domain.domain.service;

import org.apaas.domain.domain.entity.Order;
import org.apaas.domain.domain.repository.OrderRepository;
import org.apaas.domain.domain.specification.Specification;
import reactor.core.publisher.Mono;

/**
 * 订单领域服务
 * 处理订单相关的复杂业务逻辑
 *
 * @author ivan
 */
public class OrderDomainService extends AbstractDomainService<Order, Long, OrderRepository> {
    
    public OrderDomainService(OrderRepository repository) {
        super(repository);
    }
    
    /**
     * 创建订单
     *
     * @param order 订单
     * @return 创建后的订单
     */
    public Mono<Order> createOrder(Order order) {
        // 设置订单状态为待支付
        order.setStatus("PENDING_PAYMENT");
        order.setOrderTime(java.time.LocalDateTime.now());
        
        // 保存订单
        return repository.save(order);
    }
    
    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @return 取消后的订单
     */
    public Mono<Order> cancelOrder(Long orderId) {
        return repository.findById(orderId).switchIfEmpty(Mono.error(new RuntimeException("订单不存在"))).flatMap(order -> {
            if ("COMPLETED".equals(order.getStatus())) {
                return Mono.error(new RuntimeException("已完成的订单无法取消"));
            }
            order.setStatus("CANCELLED");
            return repository.save(order);
        });
    }
    
    /**
     * 根据规范验证并创建订单
     *
     * @param order 订单
     * @param specification 订单规范
     * @return 创建后的订单
     */
    public Mono<Order> createOrderWithSpecification(Order order, Specification<Order> specification) {
        return validateAndSave(order, specification);
    }
}