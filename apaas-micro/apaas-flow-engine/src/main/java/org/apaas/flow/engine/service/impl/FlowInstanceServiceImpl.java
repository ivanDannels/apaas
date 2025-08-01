package org.apaas.flow.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.flow.engine.domain.dto.FlowInstanceDTO;
import org.apaas.flow.engine.domain.dto.StartInstanceDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.entity.FlowInstance;
import org.apaas.flow.engine.entity.FlowTask;
import org.apaas.flow.engine.mapper.FlowInstanceMapper;
import org.apaas.flow.engine.mapper.FlowDefinitionMapper;
import org.apaas.flow.engine.service.FlowInstanceService;
import org.apaas.flow.engine.service.FlowTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程实例服务实现
 */
@Service
public class FlowInstanceServiceImpl extends ServiceImpl<FlowInstanceMapper, FlowInstance> implements FlowInstanceService {

    @Resource
    private FlowInstanceMapper flowInstanceMapper;

    @Resource
    private FlowDefinitionMapper flowDefinitionMapper;

    @Resource
    private FlowTaskService flowTaskService;

    @Override
    public IPage<FlowInstance> selectFlowInstancePage(FlowInstanceDTO query) {
        Page<FlowInstance> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<FlowInstance> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(query.getDefinitionId() != null, FlowInstance::getDefinitionId, query.getDefinitionId())
               .eq(query.getStatus() != null, FlowInstance::getStatus, query.getStatus())
               .eq(query.getStartUserId() != null, FlowInstance::getStartUserId, query.getStartUserId())
               .eq(FlowInstance::getTenantId, SecurityUtils.getTenantId())
               .orderByDesc(FlowInstance::getCreateTime);
        return flowInstanceMapper.selectPage(page, wrapper);
    }

    @Override
    public Map<String, Object> getFlowInstanceDetail(Long id) {
        FlowInstance instance = flowInstanceMapper.selectById(id);
        if (instance == null) {
            throw new RuntimeException("流程实例不存在");
        }
        // 获取流程定义信息
        FlowDefinition definition = flowDefinitionMapper.selectById(instance.getDefinitionId());
        // 获取当前任务信息
        FlowTask currentTask = flowTaskService.getCurrentTaskByInstanceId(id);
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("instance", instance);
        result.put("definition", definition);
        result.put("currentTask", currentTask);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long startInstance(StartInstanceDTO startInstanceDTO) {
        // 获取流程定义
        FlowDefinition definition = flowDefinitionMapper.selectById(startInstanceDTO.getDefinitionId());
        if (definition == null) {
            throw new RuntimeException("流程定义不存在");
        }
        if (definition.getStatus() != 1) {
            throw new RuntimeException("流程定义未发布，无法启动实例");
        }

        // 创建流程实例
        FlowInstance instance = new FlowInstance();
        instance.setDefinitionId(definition.getId());
        instance.setDefinitionName(definition.getName());
        instance.setDefinitionCode(definition.getCode());
        instance.setDefinitionVersion(definition.getVersion());
        instance.setBusinessKey(startInstanceDTO.getBusinessKey());
        instance.setBusinessData(startInstanceDTO.getBusinessData());
        instance.setStatus(0); // 运行中
        instance.setStartUserId(SecurityUtils.getUserId());
        instance.setStartUserName(SecurityUtils.getUsername());
        instance.setStartTime(LocalDateTime.now());
        instance.setTenantId(SecurityUtils.getTenantId());
        instance.setCreateBy(SecurityUtils.getUsername());
        instance.setUpdateBy(SecurityUtils.getUsername());
        instance.setCreateTime(LocalDateTime.now());
        instance.setUpdateTime(LocalDateTime.now());
        flowInstanceMapper.insert(instance);

        // 解析流程定义并创建第一个任务节点
        createFirstTask(instance, definition);

        return instance.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean terminateInstance(Long id) {
        FlowInstance instance = flowInstanceMapper.selectById(id);
        if (instance == null) {
            throw new RuntimeException("流程实例不存在");
        }
        if (instance.getStatus() != 0 && instance.getStatus() != 3) {
            throw new RuntimeException("只有运行中或暂停的流程实例可以终止");
        }

        // 更新实例状态
        instance.setStatus(2); // 已终止
        instance.setEndTime(LocalDateTime.now());
        instance.setUpdateBy(SecurityUtils.getUsername());
        instance.setUpdateTime(LocalDateTime.now());
        flowInstanceMapper.updateById(instance);

        // 终止所有未完成的任务
        flowTaskService.terminateTasksByInstanceId(id);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean suspendInstance(Long id) {
        FlowInstance instance = flowInstanceMapper.selectById(id);
        if (instance == null) {
            throw new RuntimeException("流程实例不存在");
        }
        if (instance.getStatus() != 0) {
            throw new RuntimeException("只有运行中的流程实例可以暂停");
        }

        // 更新实例状态
        instance.setStatus(3); // 已暂停
        instance.setUpdateBy(SecurityUtils.getUsername());
        instance.setUpdateTime(LocalDateTime.now());
        flowInstanceMapper.updateById(instance);

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resumeInstance(Long id) {
        FlowInstance instance = flowInstanceMapper.selectById(id);
        if (instance == null) {
            throw new RuntimeException("流程实例不存在");
        }
        if (instance.getStatus() != 3) {
            throw new RuntimeException("只有暂停的流程实例可以恢复");
        }

        // 更新实例状态
        instance.setStatus(0); // 运行中
        instance.setUpdateBy(SecurityUtils.getUsername());
        instance.setUpdateTime(LocalDateTime.now());
        flowInstanceMapper.updateById(instance);

        return true;
    }

    @Override
    public Map<String, Object> getInstanceTasks(Long instanceId) {
        FlowInstance instance = flowInstanceMapper.selectById(instanceId);
        if (instance == null) {
            throw new RuntimeException("流程实例不存在");
        }

        Map<String, Object> result = new HashMap<>();
        // 获取所有任务
        List<FlowTask> tasks = flowTaskService.getTasksByInstanceId(instanceId);
        // 获取当前任务
        FlowTask currentTask = flowTaskService.getCurrentTaskByInstanceId(instanceId);

        result.put("tasks", tasks);
        result.put("currentTask", currentTask);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handleTask(Long taskId, Map<String, Object> variables) {
        // 实现任务处理逻辑，包括任务状态更新、流程流转等
        FlowTask task = flowTaskService.getById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        // 校验任务权限
        if (!SecurityUtils.getUserId().equals(task.getAssigneeId())) {
            throw new RuntimeException("没有权限处理此任务");
        }

        // 更新任务状态
        task.setStatus(2); // 已完成
        task.setHandleTime(LocalDateTime.now());
        task.setHandleUserId(SecurityUtils.getUserId());
        task.setHandleUserName(SecurityUtils.getUsername());
        task.setVariables(variables.toString());
        flowTaskService.updateById(task);

        // 获取流程实例
        FlowInstance instance = flowInstanceMapper.selectById(task.getInstanceId());

        // 流转到下一节点（实际项目中应根据流程定义JSON解析下一个节点）
        // 此处简化处理，直接结束流程
        instance.setStatus(1); // 已完成
        instance.setEndTime(LocalDateTime.now());
        instance.setUpdateBy(SecurityUtils.getUsername());
        instance.setUpdateTime(LocalDateTime.now());
        flowInstanceMapper.updateById(instance);

        return true;
    }

    /**
     * 创建第一个任务节点
     */
    private void createFirstTask(FlowInstance instance, FlowDefinition definition) {
        // 实际项目中应根据流程定义JSON解析第一个节点
        // 此处简化处理，默认创建一个开始节点任务
        FlowTask task = new FlowTask();
        task.setInstanceId(instance.getId());
        task.setInstanceName(instance.getDefinitionName());
        task.setDefinitionId(instance.getDefinitionId());
        task.setDefinitionVersion(instance.getDefinitionVersion());
        task.setNodeId("start");
        task.setNodeName("开始节点");
        task.setNodeType(0); // 开始节点
        task.setStatus(1); // 处理中
        task.setAssigneeId(instance.getStartUserId());
        task.setAssigneeName(instance.getStartUserName());
        task.setCreateTime(LocalDateTime.now());
        task.setCreateBy(instance.getCreateBy());
        task.setTenantId(instance.getTenantId());
        flowTaskService.save(task);

        // 更新流程实例当前节点信息
        instance.setCurrentNodeId(task.getNodeId());
        instance.setCurrentNodeName(task.getNodeName());
        flowInstanceMapper.updateById(instance);
    }
}