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
package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 通知实体类
 */
@Data
@SuperBuilder
@Table("notification")
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity<Long> {
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 内容
     */
    private String content;
    
    /**
     * 接收人ID
     */
    private Long receiverId;
    
    /**
     * 接收人名称
     */
    private String receiverName;
    
    /**
     * 发送人ID
     */
    private Long senderId;
    
    /**
     * 发送人名称
     */
    private String senderName;
    
    /**
     * 通知类型
     */
    private String type;
    
    /**
     * 通知渠道
     */
    private String channel;
    
    /**
     * 关联业务ID
     */
    private String businessId;
    
    /**
     * 关联业务类型
     */
    private String businessType;
    
    /**
     * 阅读状态 0:未读 1:已读
     */
    private Integer readStatus;
    
    /**
     * 阅读时间
     */
    private LocalDateTime readTime;
    
    /**
     * 发送状态 0:未发送 1:已发送 2:发送失败
     */
    private Integer sendStatus;
    
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    
    /**
     * 失败原因
     */
    private String failReason;
    
    /**
     * 模板ID
     */
    private Long templateId;
    
    /**
     * 租户ID
     */
    private Long tenantId;
}