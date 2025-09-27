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
package org.apaas.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单聚合根
 * 聚合根是聚合的入口点，负责维护聚合内部的一致性
 *
 * @author ivan
 */
@Data
@Table("orders")
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity implements AggregateRoot<Long> {
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 客户ID
     */
    private Long customerId;
    
    /**
     * 订单状态
     */
    private String status;
    
    /**
     * 订单总金额
     */
    private Money totalAmount;
    
    /**
     * 下单时间
     */
    private LocalDateTime orderTime;
    
    /**
     * 订单项列表
     */
    private List<OrderItem> orderItems;
    
    /**
     * 构造函数
     */
    public Order() {
        this.orderItems = new ArrayList<>();
    }
    
    /**
     * 添加订单项
     *
     * @param orderItem 订单项
     */
    public void addOrderItem(OrderItem orderItem) {
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        this.orderItems.add(orderItem);
        // 重新计算总金额
        recalculateTotalAmount();
    }
    
    /**
     * 移除订单项
     *
     * @param orderItem 订单项
     */
    public void removeOrderItem(OrderItem orderItem) {
        if (this.orderItems != null) {
            this.orderItems.remove(orderItem);
            // 重新计算总金额
            recalculateTotalAmount();
        }
    }
    
    /**
     * 重新计算订单总金额
     */
    private void recalculateTotalAmount() {
        if (this.orderItems == null || this.orderItems.isEmpty()) {
            this.totalAmount = new Money(java.math.BigDecimal.ZERO, "CNY");
            return;
        }
        
        Money total = this.orderItems.get(0).getPrice();
        for (int i = 1; i < this.orderItems.size(); i++) {
            total = total.add(this.orderItems.get(i).getPrice());
        }
        this.totalAmount = total;
    }
    
    /**
     * 获取聚合根标识
     *
     * @return 聚合根标识
     */
    @Override
    public Long getId() {
        return super.getId();
    }
    
    /**
     * 设置聚合根标识
     *
     * @param id 聚合根标识
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}