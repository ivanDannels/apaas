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
package org.apaas.core.enums;

/**
 * 业务操作类型
 */
public enum BusinessType {
    
    /**
     * 其它
     */
    OTHER,
    
    /**
     * 新增
     */
    INSERT,
    
    /**
     * 修改
     */
    UPDATE,
    
    /**
     * 删除
     */
    DELETE,
    
    /**
     * 授权
     */
    GRANT,
    
    /**
     * 导出
     */
    EXPORT,
    
    /**
     * 导入
     */
    IMPORT,
    
    /**
     * 强退
     */
    FORCE,
    
    /**
     * 生成代码
     */
    GENCODE,
    
    /**
     * 清空数据
     */
    CLEAN,
    
    /**
     * 登录
     */
    LOGIN,
    
    /**
     * 登出
     */
    LOGOUT,
    
    /**
     * 审批
     */
    APPROVE,
    
    /**
     * 驳回
     */
    REJECT,
    
    /**
     * 转办
     */
    TRANSFER,
    
    /**
     * 委派
     */
    DELEGATE,
    
    /**
     * 流程启动
     */
    FLOW_START,
    
    /**
     * 流程终止
     */
    FLOW_TERMINATE
}