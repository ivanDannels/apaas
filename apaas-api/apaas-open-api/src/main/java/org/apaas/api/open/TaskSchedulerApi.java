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
package org.apaas.api.open;

import org.apaas.core.query.PageResult;
import org.apaas.api.open.dto.ScheduleTaskDTO;
import org.apaas.api.open.dto.TaskExecutionLogDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 任务调度服务API
 */
@RequestMapping("/open/task-scheduler")
public interface TaskSchedulerApi {
    
    /**
     * 获取任务列表
     */
    @GetMapping("/tasks")
    Mono<PageResult<ScheduleTaskDTO>> getTasks();
    
    /**
     * 获取任务详情
     */
    @GetMapping("/tasks/{id}")
    Mono<ScheduleTaskDTO> getTask(@PathVariable("id") String id);
    
    /**
     * 创建任务
     */
    @PostMapping("/tasks")
    Mono<ScheduleTaskDTO> createTask(@RequestBody ScheduleTaskDTO taskDTO);
    
    /**
     * 更新任务
     */
    @PutMapping("/tasks/{id}")
    Mono<ScheduleTaskDTO> updateTask(@PathVariable("id") String id, @RequestBody ScheduleTaskDTO taskDTO);
    
    /**
     * 启用/禁用任务
     */
    @PutMapping("/tasks/{id}/status")
    Mono<Void> changeTaskStatus(@PathVariable("id") String id, @RequestParam boolean enabled);
    
    /**
     * 删除任务
     */
    @DeleteMapping("/tasks/{id}")
    Mono<Void> deleteTask(@PathVariable("id") String id);
    
    /**
     * 手动触发任务
     */
    @PostMapping("/tasks/{id}/trigger")
    Mono<Void> triggerTask(@PathVariable("id") String id);
    
    /**
     * 获取任务执行日志
     */
    @GetMapping("/tasks/{id}/logs")
    Mono<PageResult<TaskExecutionLogDTO>> getTaskLogs(@PathVariable("id") String id);
    
    /**
     * 获取任务执行状态
     */
    @GetMapping("/tasks/{id}/status")
    Mono<String> getTaskExecutionStatus(@PathVariable("id") String id);
    
    /**
     * 暂停任务执行
     */
    @PostMapping("/tasks/{id}/pause")
    Mono<Void> pauseTaskExecution(@PathVariable("id") String id);
    
    /**
     * 恢复任务执行
     */
    @PostMapping("/tasks/{id}/resume")
    Mono<Void> resumeTaskExecution(@PathVariable("id") String id);
}