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
package org.apaas.system.specification;

import org.apaas.domain.domain.specification.Specification;
import org.apaas.system.entity.DataDictionaryAggregate;

/**
 * 数据字典规范类
 * 用于封装数据字典相关的业务规则
 *
 * @author ivan
 */
public class DataDictionarySpecification {
    
    /**
     * 数据字典名称不能为空的规范
     */
    public static Specification<DataDictionaryAggregate> nonEmptyName() {
        return dictionary -> dictionary.getName() != null && !dictionary.getName().trim().isEmpty();
    }
    
    /**
     * 数据字典编码格式正确的规范
     */
    public static Specification<DataDictionaryAggregate> validCodeFormat() {
        return dictionary -> {
            if (dictionary.getCode() == null || dictionary.getCode().trim().isEmpty()) {
                return false;
            }
            return dictionary.getCode().matches("^[A-Z0-9_]+$");
        };
    }
    
    /**
     * 数据字典类型必须有效的规范
     */
    public static Specification<DataDictionaryAggregate> validType() {
        return dictionary -> dictionary.getType() != null && (dictionary.getType() == 0 || dictionary.getType() == 1);
    }
    
    /**
     * 数据字典状态必须有效的规范
     */
    public static Specification<DataDictionaryAggregate> validStatus() {
        return dictionary -> dictionary.getStatus() != null && (dictionary.getStatus() == 0 || dictionary.getStatus() == 1);
    }
    
    /**
     * 系统字典不能被删除的规范
     */
    public static Specification<DataDictionaryAggregate> notSystemDictionary() {
        return dictionary -> {
            if (dictionary.getCode() == null) {
                return true;
            }
            return !dictionary.getCode().startsWith("SYS_");
        };
    }
}