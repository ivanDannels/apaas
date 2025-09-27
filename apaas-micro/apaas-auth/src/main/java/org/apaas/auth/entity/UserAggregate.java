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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.domain.entity.AggregateRoot;
import org.apaas.domain.entity.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户聚合根
 * 聚合根是聚合的入口点，负责维护聚合内部的一致性
 *
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_user")
@EqualsAndHashCode(callSuper = true)
public class UserAggregate extends BaseEntity implements AggregateRoot<Long> {
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 手机号码
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 性别：0-男，1-女
     */
    private Integer gender;
    
    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;
    
    /**
     * 登录IP
     */
    private String loginIp;
    
    /**
     * 登录时间
     */
    private LocalDateTime loginDate;
    
    /**
     * 登出时间
     */
    private LocalDateTime logoutDate;
    
    /**
     * 删除标志：0-未删除，1-已删除
     */
    private Integer deleted = 0;
    
    /**
     * 用户角色列表
     */
    private List<UserRole> roles;
    
    /**
     * 用户组织列表
     */
    private List<UserOrganization> organizations;
    
    /**
     * 用户岗位列表
     */
    private List<UserPosition> positions;
    
    /**
     * 用户租户列表
     */
    private List<UserTenant> tenants;
    
    /**
     * 获取聚合根标识
     *
     * @return 聚合根标识
     */
    @Override
    public Long getId() {
        return super.getId();
    }
    
    /**
     * 设置聚合根标识
     *
     * @param id 聚合根标识
     */
    @Override
    public void setId(Long id) {
        super.setId(id);
    }
    
    /**
     * 验证用户信息是否有效
     *
     * @return 是否有效
     */
    public boolean isValid() {
        return username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty();
    }
    
    /**
     * 检查用户是否已删除
     *
     * @return 是否已删除
     */
    public boolean isDeleted() {
        return deleted != null && deleted == 1;
    }
}