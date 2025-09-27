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
package org.apaas.domain.specification;

import org.apaas.domain.entity.Order;

import java.math.BigDecimal;

/**
 * 订单规范类
 * 用于封装订单相关的业务规则
 *
 * @author ivan
 */
public class OrderSpecification {
    
    /**
     * 订单金额不能为负数的规范
     */
    public static Specification<Order> nonNegativeAmount() {
        return order -> order.getTotalAmount() == null || 
                      order.getTotalAmount().getAmount().compareTo(BigDecimal.ZERO) >= 0;
    }
    
    /**
     * 订单必须包含订单项的规范
     */
    public static Specification<Order> hasOrderItems() {
        return order -> order.getOrderItems() != null && !order.getOrderItems().isEmpty();
    }
    
    /**
     * 订单状态必须是有效状态的规范
     */
    public static Specification<Order> validStatus() {
        return order -> {
            String status = order.getStatus();
            return status == null || 
                  "PENDING_PAYMENT".equals(status) || 
                  "PAID".equals(status) || 
                  "SHIPPED".equals(status) || 
                  "COMPLETED".equals(status) || 
                  "CANCELLED".equals(status);
        };
    }
    
    /**
     * 订单总金额必须与订单项金额一致的规范
     */
    public static Specification<Order> consistentTotalAmount() {
        return order -> {
            if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
                return order.getTotalAmount() == null || 
                      order.getTotalAmount().getAmount().compareTo(BigDecimal.ZERO) == 0;
            }
            
            // 计算订单项总金额
            BigDecimal itemsTotal = order.getOrderItems().stream()
                    .map(item -> item.getSubtotal().getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            return order.getTotalAmount() != null && 
                  order.getTotalAmount().getAmount().compareTo(itemsTotal) == 0;
        };
    }
}