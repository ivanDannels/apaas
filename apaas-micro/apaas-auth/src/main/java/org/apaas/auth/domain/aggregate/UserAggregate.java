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
package org.apaas.auth.domain.aggregate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.auth.domain.entity.User;
import org.apaas.auth.domain.entity.UserRole;
import org.apaas.auth.domain.entity.UserOrganization;
import org.apaas.auth.domain.entity.UserPosition;
import org.apaas.auth.domain.entity.UserTenant;

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
@EqualsAndHashCode(callSuper = true)
public class UserAggregate extends User {
    
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
        return getUsername() != null && !getUsername().trim().isEmpty() && getPassword() != null && !getPassword().trim().isEmpty();
    }
    
    /**
     * 检查用户是否已删除
     *
     * @return 是否已删除
     */
    public boolean isDeleted() {
        return getDeleted() != null && getDeleted() == 1;
    }
    
    // 添加缺失的getter方法
    public String getUsername() {
        return super.getUsername();
    }
    
    public String getPassword() {
        return super.getPassword();
    }
    
    public String getNickname() {
        return super.getNickname();
    }
    
    public String getPhone() {
        return super.getPhone();
    }
    
    public String getEmail() {
        return super.getEmail();
    }
    
    public String getAvatar() {
        return super.getAvatar();
    }
    
    public Integer getGender() {
        return super.getGender();
    }
    
    public Integer getStatus() {
        return super.getStatus();
    }
    
    public String getLoginIp() {
        return super.getLoginIp();
    }
    
    public LocalDateTime getLoginDate() {
        return super.getLoginDate();
    }
    
    public LocalDateTime getLogoutDate() {
        return super.getLogoutDate();
    }
    
    public Integer getDeleted() {
        return super.getDeleted();
    }
    
    // 添加关联对象的getter和setter方法
    public List<UserRole> getRoles() {
        return roles;
    }
    
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
    
    public List<UserOrganization> getOrganizations() {
        return organizations;
    }
    
    public void setOrganizations(List<UserOrganization> organizations) {
        this.organizations = organizations;
    }
    
    public List<UserPosition> getPositions() {
        return positions;
    }
    
    public void setPositions(List<UserPosition> positions) {
        this.positions = positions;
    }
    
    public List<UserTenant> getTenants() {
        return tenants;
    }
    
    public void setTenants(List<UserTenant> tenants) {
        this.tenants = tenants;
    }
}