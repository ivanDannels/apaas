package org.apaas.flow.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.flow.engine.entity.FlowTask;
import java.util.List;

/**
 * 流程任务服务接口
 */
public interface FlowTaskService extends IService<FlowTask> {
    /**
     * 获取实例的当前任务
     */
    FlowTask getCurrentTaskByInstanceId(Long instanceId);

    /**
     * 获取实例的所有任务
     */
    List<FlowTask> getTasksByInstanceId(Long instanceId);

    /**
     * 终止实例的所有未完成任务
     */
    boolean terminateTasksByInstanceId(Long instanceId);

    /**
     * 分配任务给指定用户
     */
    boolean assignTask(Long taskId, Long userId, String userName);

    /**
     * 退回任务到上一节点
     */
    boolean returnTask(Long taskId, String comment);
}