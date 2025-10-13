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
package org.apaas.auth.interfaces.rest.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户注册对象
 * @author ivan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户注册对象")
public class RegisterBody extends LoginBody {
    
    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;
    
    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;
    
    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String mobile;
    
    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;
}