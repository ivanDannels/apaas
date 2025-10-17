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
package org.apaas.design.application.assembler;

import org.apaas.design.application.dto.DataSyncTaskDTO;
import org.apaas.design.domain.model.DataSyncTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数据同步任务装配器
 * 负责DTO与领域对象之间的转换
 *
 * @author ivan
 */
@Mapper
public interface DataSyncTaskAssembler {
    
    DataSyncTaskAssembler INSTANCE = Mappers.getMapper(DataSyncTaskAssembler.class);
    
    /**
     * 将数据同步任务DTO转换为数据同步任务实体
     */
    DataSyncTask convertDtoToEntity(DataSyncTaskDTO dataSyncTaskDTO);
    
    /**
     * 将数据同步任务实体转换为数据同步任务DTO
     */
    DataSyncTaskDTO convertEntityToDto(DataSyncTask dataSyncTask);
    
    /**
     * 将数据同步任务实体列表转换为数据同步任务DTO列表
     */
    List<DataSyncTaskDTO> convertEntityListToDtoList(List<DataSyncTask> dataSyncTaskList);
}