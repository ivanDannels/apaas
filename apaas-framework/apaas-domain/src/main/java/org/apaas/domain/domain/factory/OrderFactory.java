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
package org.apaas.domain.domain.factory;

import org.apaas.domain.domain.vo.Money;
import org.apaas.domain.domain.entity.Order;
import org.apaas.domain.domain.entity.OrderItem;

import java.util.Currency;

/**
 * 订单工厂类
 * 用于创建复杂的订单对象，封装对象创建逻辑
 *
 * @author ivan
 */
public class OrderFactory implements EntityFactory<Order, Long> {
    
    /**
     * 创建订单对象
     *
     * @param id 订单ID
     * @param args 创建参数（客户ID，订单号）
     * @return 订单对象
     */
    @Override
    public Order create(Long id, Object... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("创建订单需要客户ID和订单号参数");
        }
        
        Long customerId = (Long) args[0];
        String orderNo = (String) args[1];
        
        return Order.builder().id(id).customerId(customerId).orderNo(orderNo).build();
    }
    
    /**
     * 创建订单对象（无参）
     *
     * @return 订单对象
     */
    @Override
    public Order create() {
        return Order.builder().build();
    }
    
    /**
     * 从原型创建订单对象
     *
     * @param prototype 原型对象
     * @return 订单对象
     */
    @Override
    public Order createFrom(Order prototype) {
        Order order = Order.builder().id(prototype.getId()).customerId(prototype.getCustomerId()).orderNo(prototype.getOrderNo()).status(prototype.getStatus()).totalAmount(prototype.getTotalAmount()).orderTime(prototype.getOrderTime()).build();
        
        // 复制订单项
        if (prototype.getOrderItems() != null) {
            prototype.getOrderItems().forEach(item -> {
                OrderItem newItem = OrderItem.builder().id(item.getId()).orderId(item.getOrderId()).productId(item.getProductId()).productName(item.getProductName()).price(item.getPrice()).quantity(item.getQuantity()).subtotal(item.getSubtotal()).build();
                order.addOrderItem(newItem);
            });
        }
        
        return order;
    }
    
    /**
     * 创建订单项
     *
     * @param orderId 订单ID
     * @param productId 商品ID
     * @param productName 商品名称
     * @param price 商品单价
     * @param quantity 商品数量
     * @return 订单项对象
     */
    public OrderItem createOrderItem(Long orderId, Long productId, String productName, Money price, Integer quantity) {
        OrderItem orderItem = OrderItem.builder().orderId(orderId).productId(productId).productName(productName).price(price).quantity(quantity).build();
        
        // 计算小计
        orderItem.calculateSubtotal();
        
        return orderItem;
    }
}