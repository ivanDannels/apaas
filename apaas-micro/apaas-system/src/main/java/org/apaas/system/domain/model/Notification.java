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
package org.apaas.system.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.BaseEntity;
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
    
    /**
     * 获取标题
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 设置标题
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 获取内容
     */
    public String getContent() {
        return content;
    }
    
    /**
     * 设置内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * 获取接收人ID
     */
    public Long getReceiverId() {
        return receiverId;
    }
    
    /**
     * 设置接收人ID
     */
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
    
    /**
     * 获取通知类型
     */
    public String getType() {
        return type;
    }
    
    /**
     * 设置通知类型
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * 获取阅读状态
     */
    public Integer getReadStatus() {
        return readStatus;
    }
    
    /**
     * 设置阅读状态
     */
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }
    
    /**
     * 获取发送状态
     */
    public Integer getSendStatus() {
        return sendStatus;
    }
    
    /**
     * 设置发送状态
     */
    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }
    
    /**
     * 设置阅读状态为已读
     */
    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }
    
    /**
     * 设置阅读时间
     */
    public void setReadTime(LocalDateTime readTime) {
        this.readTime = readTime;
    }
    
    /**
     * 设置发送状态为已发送
     */
    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }
    
    /**
     * 设置发送时间
     */
    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}