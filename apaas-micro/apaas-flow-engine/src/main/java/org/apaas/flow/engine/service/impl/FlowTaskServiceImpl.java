package org.apaas.flow.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.flow.engine.entity.FlowTask;
import org.apaas.flow.engine.mapper.FlowTaskMapper;
import org.apaas.flow.engine.service.FlowTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 流程任务服务实现
 */
@Service
public class FlowTaskServiceImpl extends ServiceImpl<FlowTaskMapper, FlowTask> implements FlowTaskService {

    @Resource
    private FlowTaskMapper flowTaskMapper;

    @Override
    public FlowTask getCurrentTaskByInstanceId(Long instanceId) {
        LambdaQueryWrapper<FlowTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowTask::getInstanceId, instanceId)
               .in(FlowTask::getStatus, 0, 1) // 未开始或处理中
               .eq(FlowTask::getTenantId, SecurityUtils.getTenantId())
               .orderByAsc(FlowTask::getCreateTime);
        return flowTaskMapper.selectOne(wrapper);
    }

    @Override
    public List<FlowTask> getTasksByInstanceId(Long instanceId) {
        LambdaQueryWrapper<FlowTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowTask::getInstanceId, instanceId)
               .eq(FlowTask::getTenantId, SecurityUtils.getTenantId())
               .orderByAsc(FlowTask::getCreateTime);
        return flowTaskMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean terminateTasksByInstanceId(Long instanceId) {
        LambdaQueryWrapper<FlowTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowTask::getInstanceId, instanceId)
               .in(FlowTask::getStatus, 0, 1) // 未开始或处理中
               .eq(FlowTask::getTenantId, SecurityUtils.getTenantId());

        FlowTask updateTask = new FlowTask();
        updateTask.setStatus(3); // 已终止
        updateTask.setHandleTime(LocalDateTime.now());
        updateTask.setHandleUserId(SecurityUtils.getUserId());
        updateTask.setHandleUserName(SecurityUtils.getUsername());
        updateTask.setUpdateBy(SecurityUtils.getUsername());
        updateTask.setUpdateTime(LocalDateTime.now());

        return flowTaskMapper.update(updateTask, wrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignTask(Long taskId, Long userId, String userName) {
        FlowTask task = flowTaskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        task.setAssigneeId(userId);
        task.setAssigneeName(userName);
        task.setStatus(1); // 处理中
        task.setStartTime(LocalDateTime.now());
        task.setUpdateBy(SecurityUtils.getUsername());
        task.setUpdateTime(LocalDateTime.now());

        return flowTaskMapper.updateById(task) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean returnTask(Long taskId, String comment) {
        FlowTask task = flowTaskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        // 设置任务为已退回
        task.setStatus(4); // 已退回
        task.setComment(comment);
        task.setHandleTime(LocalDateTime.now());
        task.setHandleUserId(SecurityUtils.getUserId());
        task.setHandleUserName(SecurityUtils.getUsername());
        task.setUpdateBy(SecurityUtils.getUsername());
        task.setUpdateTime(LocalDateTime.now());
        flowTaskMapper.updateById(task);

        // 实际项目中应根据流程定义创建上一节点任务
        // 此处简化处理，不创建新任务
        return true;
    }
}