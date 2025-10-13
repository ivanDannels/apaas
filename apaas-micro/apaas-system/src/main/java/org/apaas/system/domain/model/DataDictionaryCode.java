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
import org.apaas.domain.vo.BaseValueObject;

/**
 * 数据字典编码值对象
 * 表示数据字典的编码，确保编码的唯一性和格式正确性
 *
 * @author ivan
 */
@Data
public class DataDictionaryCode extends BaseValueObject {
    
    /**
     * 字典编码
     */
    private final String code;
    
    /**
     * 构造函数
     *
     * @param code 字典编码
     */
    public DataDictionaryCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("字典编码不能为空");
        }
        if (!code.matches("^[A-Z0-9_]+$")) {
            throw new IllegalArgumentException("字典编码只能包含大写字母、数字和下划线");
        }
        this.code = code.toUpperCase();
    }
    
    /**
     * 获取编码前缀
     *
     * @return 编码前缀
     */
    public String getPrefix() {
        int underscoreIndex = code.indexOf('_');
        if (underscoreIndex > 0) {
            return code.substring(0, underscoreIndex);
        }
        return code;
    }
    
    /**
     * 判断是否为系统字典编码
     *
     * @return 是否为系统字典编码
     */
    public boolean isSystemCode() {
        return code.startsWith("SYS_");
    }
    
    /**
     * 判断是否为业务字典编码
     *
     * @return 是否为业务字典编码
     */
    public boolean isBusinessCode() {
        return !isSystemCode();
    }
    
    @Override
    protected boolean equalsImpl(BaseValueObject other) {
        if (!(other instanceof DataDictionaryCode)) {
            return false;
        }
        DataDictionaryCode that = (DataDictionaryCode) other;
        return this.code.equals(that.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
    
    @Override
    public String toString() {
        return code;
    }
}