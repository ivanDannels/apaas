package org.apaas.flow.execution.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.FlowInstance;
import org.apaas.flow.execution.repository.FlowInstanceRepository;
import org.apaas.flow.execution.service.FlowRuntimeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FlowRuntimeServiceImpl extends BaseServiceImpl<FlowInstance, Long, FlowInstanceRepository> implements FlowRuntimeService {
    
    public FlowRuntimeServiceImpl(FlowInstanceRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Result<FlowInstance>> startProcessInstance(Long processId, String businessKey, Long starter) {
        // 实现流程启动逻辑
        return Mono.empty();
    }
    
    @Override
    public Mono<Result<FlowInstance>> completeActivityInstance(Long activityInstanceId, Long userId) {
        // 实现活动完成逻辑
        return Mono.empty();
    }
    
    @Override
    public Mono<Result<FlowInstance>> terminateFlowInstance(Long flowInstanceId, Long userId) {
        // 实现流程终止逻辑
        return Mono.empty();
    }
}