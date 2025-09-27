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
package org.apaas.auth.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apaas.domain.entity.BaseValueObject;

/**
 * 用户名值对象
 * 表示用户的唯一标识符，确保用户名的唯一性和格式正确性
 *
 * @author ivan
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Username extends BaseValueObject {
    
    /**
     * 用户名
     */
    private final String username;
    
    /**
     * 构造函数
     *
     * @param username 用户名
     */
    public Username(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (username.length() < 3 || username.length() > 20) {
            throw new IllegalArgumentException("用户名长度必须在3-20个字符之间");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("用户名只能包含字母、数字和下划线");
        }
        this.username = username.toLowerCase();
    }
    
    /**
     * 检查用户名是否符合格式要求
     *
     * @return 是否符合格式要求
     */
    public boolean isValidFormat() {
        return username.matches("^[a-zA-Z0-9_]{3,20}$");
    }
    
    /**
     * 检查用户名是否为管理员用户名
     *
     * @return 是否为管理员用户名
     */
    public boolean isAdmin() {
        return "admin".equals(username) || "administrator".equals(username);
    }
    
    @Override
    protected boolean equalsImpl(BaseValueObject other) {
        if (!(other instanceof Username)) {
            return false;
        }
        Username that = (Username) other;
        return this.username.equals(that.username);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode();
    }
    
    @Override
    public String toString() {
        return username;
    }
}