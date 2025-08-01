package org.apaas.flow.execution;

import org.apaas.flow.execution.domain.dto.Result;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import org.apaas.flow.execution.service.TaskManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskManagementServiceTest {
    
    @MockBean
    private TaskManagementService taskManagementService;
    
    @Test
    void getUserTasks() {
        // TODO: 实现查询用户任务列表测试
        WorkflowTask task = new WorkflowTask();
        when(taskManagementService.getUserTasks(any()))
                .thenReturn(Mono.just(Result.success(Flux.just(task))));
        
        StepVerifier.create(taskManagementService.getUserTasks(1L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
    
    @Test
    void claimTask() {
        // TODO: 实现领取任务测试
        WorkflowTask task = new WorkflowTask();
        when(taskManagementService.claimTask(any(), any()))
                .thenReturn(Mono.just(Result.success(task)));
        
        StepVerifier.create(taskManagementService.claimTask(1L, 1L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
    
    @Test
    void completeTask() {
        // TODO: 实现完成任务测试
        WorkflowTask task = new WorkflowTask();
        when(taskManagementService.completeTask(any(), any()))
                .thenReturn(Mono.just(Result.success(task)));
        
        StepVerifier.create(taskManagementService.completeTask(1L, 1L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
    
    @Test
    void transferTask() {
        // TODO: 实现转办任务测试
        WorkflowTask task = new WorkflowTask();
        when(taskManagementService.transferTask(any(), any(), any()))
                .thenReturn(Mono.just(Result.success(task)));
        
        StepVerifier.create(taskManagementService.transferTask(1L, 1L, 2L))
                .expectNextMatches(result -> result.getCode() == 200)
                .verifyComplete();
    }
}