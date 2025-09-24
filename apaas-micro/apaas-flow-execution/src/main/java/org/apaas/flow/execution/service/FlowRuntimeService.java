package org.apaas.flow.execution.service;

import org.apaas.domain.service.BaseService;
import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.FlowInstance;
import reactor.core.publisher.Mono;

public interface FlowRuntimeService extends BaseService<FlowInstance, Long> {
    
    /**
     * 启动流程实例
     *
     * @param processId 流程定义ID
     * @param businessKey 业务主键
     * @param starter 启动人
     * @return 流程实例
     */
    Mono<Result<FlowInstance>> startProcessInstance(Long processId, String businessKey, Long starter);
    
    /**
     * 完成活动实例
     *
     * @param activityInstanceId 活动实例ID
     * @param userId 用户ID
     * @return 流程实例
     */
    Mono<Result<FlowInstance>> completeActivityInstance(Long activityInstanceId, Long userId);
    
    /**
     * 终止流程实例
     *
     * @param flowInstanceId 流程实例ID
     * @param userId 用户ID
     * @return 流程实例
     */
    Mono<Result<FlowInstance>> terminateFlowInstance(Long flowInstanceId, Long userId);
}