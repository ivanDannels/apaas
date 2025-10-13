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
package org.apaas.system.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.system.domain.model.SysOperLog;
import org.apaas.system.application.dto.SysOperLogDTO;

import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import reactor.core.publisher.Mono;

/**
 * 响应式操作日志服务接口
 * @author ivan
 */
public interface ReactiveSysOperLogService extends ApplicationService<SysOperLog, Long> {
    
    /**
     * 分页查询操作日志
     */
    Mono<PageResult<SysOperLogDTO>> selectPage(Query query);
}