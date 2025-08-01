package org.apaas.flow.execution;

import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.FlowInstance;
import org.apaas.flow.execution.service.FlowRuntimeService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class FlowRuntimeServiceTest {
    
    @MockBean
    private FlowRuntimeService flowRuntimeService;
    
    @Test
    void startProcessInstance() {
        // TODO: 实现启动流程实例测试
        FlowInstance flowInstance = new FlowInstance();
        when(flowRuntimeService.startProcessInstance(any(), any(), any()))
                .thenReturn(Mono.just(Result.success(flowInstance)));
        
        StepVerifier.create(flowRuntimeService.startProcessInstance(1L, "key", 1L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
    
    @Test
    void completeActivityInstance() {
        // TODO: 实现完成活动实例测试
        FlowInstance flowInstance = new FlowInstance();
        when(flowRuntimeService.completeActivityInstance(any(), any()))
                .thenReturn(Mono.just(Result.success(flowInstance)));
        
        StepVerifier.create(flowRuntimeService.completeActivityInstance(1L, 1L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
    
    @Test
    void terminateFlowInstance() {
        // TODO: 实现终止流程实例测试
        FlowInstance flowInstance = new FlowInstance();
        when(flowRuntimeService.terminateFlowInstance(any(), any()))
                .thenReturn(Mono.just(Result.success(flowInstance)));
        
        StepVerifier.create(flowRuntimeService.terminateFlowInstance(1L, 1L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
    
}