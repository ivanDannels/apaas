package org.apaas.flow.engine.service.reactive;

import org.apaas.flow.engine.domain.entity.TaskInstance;
import org.apaas.common.web.domain.PageResult;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

/**
 * 响应式任务实例服务接口
 */
public interface ReactiveTaskInstanceService {

    /**
     * 根据任务实例ID获取任务实例
     *
     * @param taskId 任务实例ID
     * @return 任务实例信息
     */
    Mono<TaskInstance> getTaskInstanceById(String taskId);

    /**
     * 分页获取任务实例列表
     *
     * @param pageable 分页参数
     * @return 任务实例分页结果
     */
    Mono<PageResult<TaskInstance>> getTaskInstancePage(Pageable pageable);

    /**
     * 完成任务实例
     *
     * @param taskId 任务实例ID
     * @param userId 用户ID
     * @param variables 任务变量
     * @return 完成结果
     */
    Mono<Void> completeTaskInstance(String taskId, Long userId, java.util.Map<String, Object> variables);

    /**
     * 拒绝任务实例
     *
     * @param taskId 任务实例ID
     * @param userId 用户ID
     * @param reason 拒绝原因
     * @return 拒绝结果
     */
    Mono<Void> rejectTaskInstance(String taskId, Long userId, String reason);

    /**
     * 转办任务实例
     *
     * @param taskId 任务实例ID
     * @param fromUserId 原处理人ID
     * @param toUserId 新处理人ID
     * @return 转办结果
     */
    Mono<Void> transferTaskInstance(String taskId, Long fromUserId, Long toUserId);
}