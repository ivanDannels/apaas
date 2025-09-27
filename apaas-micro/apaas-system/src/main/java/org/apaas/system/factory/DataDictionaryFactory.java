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
package org.apaas.system.factory;

import org.apaas.domain.factory.EntityFactory;
import org.apaas.system.entity.DataDictionaryAggregate;
import org.apaas.system.entity.DataDictionaryCode;
import org.apaas.system.entity.DataDictionaryItem;

/**
 * 数据字典工厂类
 * 用于创建复杂的数据字典对象，封装对象创建逻辑
 *
 * @author ivan
 */
public class DataDictionaryFactory implements EntityFactory<DataDictionaryAggregate, Long> {
    
    /**
     * 创建数据字典聚合根
     *
     * @param id 数据字典ID
     * @param args 创建参数（名称，编码，类型，描述）
     * @return 数据字典聚合根
     */
    @Override
    public DataDictionaryAggregate create(Long id, Object... args) {
        if (args.length < 4) {
            throw new IllegalArgumentException("创建数据字典需要名称、编码、类型和描述参数");
        }
        
        String name = (String) args[0];
        String code = (String) args[1];
        Integer type = (Integer) args[2];
        String description = (String) args[3];
        
        DataDictionaryAggregate aggregate = DataDictionaryAggregate.builder()
                .id(id)
                .name(name)
                .code(code)
                .type(type)
                .status(0) // 默认状态为正常
                .description(description)
                .build();
        
        // 设置编码值对象
        DataDictionaryCode dictionaryCode = new DataDictionaryCode(code);
        aggregate.setCode(dictionaryCode.toString());
        
        return aggregate;
    }
    
    /**
     * 创建数据字典聚合根（无参）
     *
     * @return 数据字典聚合根
     */
    @Override
    public DataDictionaryAggregate create() {
        return DataDictionaryAggregate.builder()
                .status(0) // 默认状态为正常
                .build();
    }
    
    /**
     * 从原型创建数据字典聚合根
     *
     * @param prototype 原型对象
     * @return 数据字典聚合根
     */
    @Override
    public DataDictionaryAggregate createFrom(DataDictionaryAggregate prototype) {
        DataDictionaryAggregate aggregate = DataDictionaryAggregate.builder()
                .id(prototype.getId())
                .name(prototype.getName())
                .code(prototype.getCode())
                .type(prototype.getType())
                .status(prototype.getStatus())
                .description(prototype.getDescription())
                .build();
        
        // 复制字典项
        if (prototype.getItems() != null) {
            prototype.getItems().forEach(item -> {
                DataDictionaryItem newItem = DataDictionaryItem.builder()
                        .id(item.getId())
                        .dictionaryId(item.getDictionaryId())
                        .label(item.getLabel())
                        .value(item.getValue())
                        .sortOrder(item.getSortOrder())
                        .status(item.getStatus())
                        .description(item.getDescription())
                        .build();
                aggregate.addItem(newItem);
            });
        }
        
        return aggregate;
    }
    
    /**
     * 创建数据字典项
     *
     * @param dictionaryId 字典ID
     * @param label 项标签
     * @param value 项值
     * @param sortOrder 排序
     * @param description 描述
     * @return 数据字典项对象
     */
    public DataDictionaryItem createDataDictionaryItem(Long dictionaryId, String label, String value, Integer sortOrder, String description) {
        return DataDictionaryItem.builder()
                .dictionaryId(dictionaryId)
                .label(label)
                .value(value)
                .sortOrder(sortOrder)
                .status(0) // 默认状态为正常
                .description(description)
                .build();
    }
}