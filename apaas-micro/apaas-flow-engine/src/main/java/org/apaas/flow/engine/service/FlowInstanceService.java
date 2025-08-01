package org.apaas.flow.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.flow.engine.domain.dto.FlowInstanceDTO;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowInstance;
import java.util.Map;

/**
 * 流程实例服务接口
 */
public interface FlowInstanceService extends IService<FlowInstance> {
    /**
     * 分页查询流程实例
     */
    Page<FlowInstance> selectFlowInstancePage(FlowInstanceDTO query);

    /**
     * 获取流程实例详情
     */
    Map<String, Object> getFlowInstanceDetail(Long id);

    /**
     * 启动流程实例
     */
    Long startInstance(StartInstanceDTO startInstanceDTO);

    /**
     * 终止流程实例
     */
    boolean terminateInstance(Long id);

    /**
     * 暂停流程实例
     */
    boolean suspendInstance(Long id);

    /**
     * 恢复流程实例
     */
    boolean resumeInstance(Long id);

    /**
     * 获取流程实例的任务记录
     */
    Map<String, Object> getInstanceTasks(Long instanceId);

    /**
     * 处理流程任务
     */
    boolean handleTask(Long taskId, Map<String, Object> variables);
}