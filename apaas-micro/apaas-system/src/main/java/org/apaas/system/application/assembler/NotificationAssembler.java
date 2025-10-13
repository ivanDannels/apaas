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
package org.apaas.system.application.assembler;

import org.apaas.system.application.dto.NotificationDTO;
import org.apaas.system.domain.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 通知装配器
 * 负责DTO与领域对象之间的转换
 *
 * @author ivan
 */
@Mapper
public interface NotificationAssembler {
    
    NotificationAssembler INSTANCE = Mappers.getMapper(NotificationAssembler.class);
    
    /**
     * 将通知DTO转换为通知实体
     */
    Notification convertDtoToEntity(NotificationDTO notificationDTO);
    
    /**
     * 将通知实体转换为通知DTO
     */
    NotificationDTO convertEntityToDto(Notification notification);
    
    /**
     * 将通知实体列表转换为通知DTO列表
     */
    List<NotificationDTO> convertEntityListToDtoList(List<Notification> notificationList);
    
    /**
     * 将通知DTO列表转换为通知实体列表
     */
    List<Notification> convertDtoListToEntityList(List<NotificationDTO> notificationDtoList);
}