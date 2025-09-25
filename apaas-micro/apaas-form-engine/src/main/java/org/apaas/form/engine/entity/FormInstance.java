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
package org.apaas.form.engine.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apaas.domain.entity.BaseEntity;

/**
 * 表单实例实体
 * @author ivan
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FormInstance extends BaseEntity {
    
    /**
     * 表单定义ID
     */
    private Long formDefinitionId;
    
    /**
     * 表单数据 (JSON格式)
     */
    private String dataJson;
    
    /**
     * 提交人
     */
    private String submitter;
    
    /**
     * 提交时间
     */
    private java.time.LocalDateTime submitTime;
}