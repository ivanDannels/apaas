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
package org.apaas.auth.specification;

import org.apaas.auth.entity.UserAggregate;
import org.apaas.domain.specification.Specification;

/**
 * 用户规范类
 * 用于封装用户相关的业务规则
 *
 * @author ivan
 */
public class UserSpecification {
    
    /**
     * 用户名不能为空的规范
     */
    public static Specification<UserAggregate> nonEmptyUsername() {
        return user -> user.getUsername() != null && !user.getUsername().trim().isEmpty();
    }
    
    /**
     * 密码不能为空的规范
     */
    public static Specification<UserAggregate> nonEmptyPassword() {
        return user -> user.getPassword() != null && !user.getPassword().trim().isEmpty();
    }
    
    /**
     * 用户状态必须有效的规范
     */
    public static Specification<UserAggregate> validStatus() {
        return user -> user.getStatus() != null && (user.getStatus() == 0 || user.getStatus() == 1);
    }
    
    /**
     * 用户未被删除的规范
     */
    public static Specification<UserAggregate> notDeleted() {
        return user -> user.getDeleted() == null || user.getDeleted() == 0;
    }
    
    /**
     * 管理员用户不能被删除的规范
     */
    public static Specification<UserAggregate> notAdminUser() {
        return user -> {
            if (user.getUsername() == null) {
                return true;
            }
            return !("admin".equals(user.getUsername()) || "administrator".equals(user.getUsername()));
        };
    }
}