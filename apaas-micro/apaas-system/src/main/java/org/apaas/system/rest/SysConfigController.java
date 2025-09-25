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
package org.apaas.system.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.system.entity.SysConfig;
import org.apaas.system.service.ReactiveSysConfigService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 参数配置控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/configs")
@Tag(name = "参数配置管理", description = "参数配置管理API")
public class SysConfigController extends ReactiveBaseController<SysConfig, Long, ReactiveSysConfigService> {
    
    public SysConfigController(ReactiveSysConfigService service) {
        super(service);
    }
    
    /**
     * 修改参数配置状态
     */
    @PutMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改参数配置状态", description = "修改参数配置状态")
    @Parameters({@Parameter(name = "id", description = "参数配置ID", required = true), @Parameter(name = "status", description = "状态：0-正常，1-停用", required = true)})
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return service.changeStatus(id, status);
    }
    
    /**
     * 根据参数编码查询参数配置
     */
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据参数编码查询参数配置", description = "根据参数编码查询参数配置")
    @Parameter(name = "code", description = "参数编码", required = true)
    public Mono<SysConfig> getConfigByCode(@PathVariable String code) {
        return service.getConfigByCode(code);
    }
    
}