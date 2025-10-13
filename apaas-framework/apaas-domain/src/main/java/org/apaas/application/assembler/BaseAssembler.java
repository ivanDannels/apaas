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
package org.apaas.application.assembler;

import org.apaas.domain.entity.BaseEntity;
import org.apaas.application.dto.BaseDTO;

import java.io.Serializable;
import java.util.List;

/**
 * 基础装配器接口，定义实体与DTO之间的转换
 * @author ivan
 * @param <T> 实体类型
 * @param <D> DTO类型
 * @param <ID> 标识类型
 */
public interface BaseAssembler<T extends BaseEntity<ID>, D extends BaseDTO<ID>, ID extends Serializable> {
    
    /**
     * 将实体转换为DTO
     *
     * @param entity 实体对象
     * @return DTO对象
     */
    D toDTO(T entity);
    
    /**
     * 将DTO转换为实体
     *
     * @param dto DTO对象
     * @return 实体对象
     */
    T toEntity(D dto);
    
    /**
     * 将实体列表转换为DTO列表
     *
     * @param entities 实体列表
     * @return DTO列表
     */
    List<D> toDTOList(List<T> entities);
    
    /**
     * 将DTO列表转换为实体列表
     *
     * @param dtoList DTO列表
     * @return 实体列表
     */
    List<T> toEntityList(List<D> dtoList);
}