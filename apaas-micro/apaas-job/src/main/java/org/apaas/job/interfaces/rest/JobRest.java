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
package org.apaas.job.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.enums.OperatorType;
import org.apaas.interfaces.rest.BaseRest;
import org.apaas.job.application.dto.JobDTO;
import org.apaas.job.application.service.JobService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/jobs")
public class JobRest extends BaseRest<JobDTO, Long, JobService> {
    
    public JobRest(JobService service) {
        super(service);
    }
    
    /**
     * 启用任务
     */
    @Log(title = "启用任务", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/enable")
    @Operation(summary = "启用任务", description = "启用指定的任务")
    public Mono<Void> enableJob(@Parameter(description = "任务ID", required = true) @PathVariable Long id) {
        return service.enableJob(id);
    }
    
    /**
     * 禁用任务
     */
    @Log(title = "禁用任务", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/disable")
    @Operation(summary = "禁用任务", description = "禁用指定的任务")
    public Mono<Void> disableJob(@Parameter(description = "任务ID", required = true) @PathVariable Long id) {
        return service.disableJob(id);
    }
    
    /**
     * 手动触发任务
     */
    @Log(title = "手动触发任务", businessType = BusinessType.OTHER, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/trigger")
    @Operation(summary = "手动触发任务", description = "手动触发指定的任务执行")
    public Mono<Void> triggerJob(@Parameter(description = "任务ID", required = true) @PathVariable Long id) {
        return service.triggerJob(id);
    }
    
    /**
     * 暂停任务执行
     */
    @Log(title = "暂停任务执行", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/pause")
    @Operation(summary = "暂停任务执行", description = "暂停指定的任务执行")
    public Mono<Void> pauseJob(@Parameter(description = "任务ID", required = true) @PathVariable Long id) {
        return service.pauseJob(id);
    }
    
    /**
     * 恢复任务执行
     */
    @Log(title = "恢复任务执行", businessType = BusinessType.UPDATE, operatorType = OperatorType.MANAGE)
    @PostMapping("/{id}/resume")
    @Operation(summary = "恢复任务执行", description = "恢复指定的任务执行")
    public Mono<Void> resumeJob(@Parameter(description = "任务ID", required = true) @PathVariable Long id) {
        return service.resumeJob(id);
    }
}