package org.apaas.flow.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apaas.flow.engine.mapper.FlowDefinitionMapper;
import org.apaas.flow.engine.service.FlowDefinitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程定义服务实现
 */
@Service
public class FlowDefinitionServiceImpl extends ServiceImpl<FlowDefinitionMapper, FlowDefinition> implements FlowDefinitionService {

    @Resource
    private FlowDefinitionMapper flowDefinitionMapper;

    @Override
    public IPage<FlowDefinition> selectFlowDefinitionPage(FlowDefinitionDTO query) {
        Page<FlowDefinition> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<FlowDefinition> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getName() != null, FlowDefinition::getName, query.getName())
               .eq(query.getCategory() != null, FlowDefinition::getCategory, query.getCategory())
               .eq(query.getStatus() != null, FlowDefinition::getStatus, query.getStatus())
               .eq(FlowDefinition::getTenantId, SecurityUtils.getTenantId())
               .orderByDesc(FlowDefinition::getCreateTime);
        return flowDefinitionMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveFlowDefinition(FlowDefinition flowDefinition) {
        // 设置租户ID
        flowDefinition.setTenantId(SecurityUtils.getTenantId());
        // 设置创建人、更新人
        String username = SecurityUtils.getUsername();
        flowDefinition.setCreateBy(username);
        flowDefinition.setUpdateBy(username);
        // 设置创建时间、更新时间
        LocalDateTime now = LocalDateTime.now();
        flowDefinition.setCreateTime(now);
        flowDefinition.setUpdateTime(now);
        // 初始状态为草稿
        flowDefinition.setStatus(0);
        // 处理版本号
        handleVersion(flowDefinition);
        // 保存流程定义
        flowDefinitionMapper.insert(flowDefinition);
        return flowDefinition.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFlowDefinition(FlowDefinition flowDefinition) {
        // 设置更新人、更新时间
        flowDefinition.setUpdateBy(SecurityUtils.getUsername());
        flowDefinition.setUpdateTime(LocalDateTime.now());
        // 如果状态为已发布，则不允许修改
        FlowDefinition oldDefinition = flowDefinitionMapper.selectById(flowDefinition.getId());
        if (oldDefinition.getStatus() == 1) {
            throw new RuntimeException("已发布的流程定义不允许修改");
        }
        return flowDefinitionMapper.updateById(flowDefinition) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFlowDefinitions(Long[] ids) {
        // 检查是否有已发布的流程定义
        List<FlowDefinition> list = flowDefinitionMapper.selectBatchIds(Arrays.asList(ids));
        boolean hasPublished = list.stream().anyMatch(fd -> fd.getStatus() == 1);
        if (hasPublished) {
            throw new RuntimeException("包含已发布的流程定义，不允许删除");
        }
        return flowDefinitionMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deployFlowDefinition(Long id) {
        FlowDefinition flowDefinition = flowDefinitionMapper.selectById(id);
        if (flowDefinition == null) {
            throw new RuntimeException("流程定义不存在");
        }
        // 设置状态为已发布
        flowDefinition.setStatus(1);
        flowDefinition.setUpdateBy(SecurityUtils.getUsername());
        flowDefinition.setUpdateTime(LocalDateTime.now());
        // 如果设为默认版本，则更新其他版本为非默认
        if (flowDefinition.getIsDefault()) {
            flowDefinitionMapper.updateIsDefaultByCode(flowDefinition.getCode(), 0);
        }
        return flowDefinitionMapper.updateById(flowDefinition) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableFlowDefinition(Long id) {
        FlowDefinition flowDefinition = flowDefinitionMapper.selectById(id);
        if (flowDefinition == null) {
            throw new RuntimeException("流程定义不存在");
        }
        // 设置状态为已停用
        flowDefinition.setStatus(2);
        flowDefinition.setUpdateBy(SecurityUtils.getUsername());
        flowDefinition.setUpdateTime(LocalDateTime.now());
        return flowDefinitionMapper.updateById(flowDefinition) > 0;
    }

    @Override
    public List<FlowDefinition> getVersionsByCode(String code) {
        LambdaQueryWrapper<FlowDefinition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowDefinition::getCode, code)
               .eq(FlowDefinition::getTenantId, SecurityUtils.getTenantId())
               .orderByDesc(FlowDefinition::getVersion);
        return flowDefinitionMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long copyFlowDefinition(Long id, String newName) {
        FlowDefinition source = flowDefinitionMapper.selectById(id);
        if (source == null) {
            throw new RuntimeException("流程定义不存在");
        }
        // 创建新的流程定义
        FlowDefinition newDefinition = new FlowDefinition();
        newDefinition.setName(newName);
        newDefinition.setCode(source.getCode());
        newDefinition.setCategory(source.getCategory());
        newDefinition.setDescription(source.getDescription());
        newDefinition.setFlowJson(source.getFlowJson());
        newDefinition.setFormId(source.getFormId());
        newDefinition.setStatus(0); // 草稿状态
        newDefinition.setIsDefault(false);
        newDefinition.setTenantId(SecurityUtils.getTenantId());
        newDefinition.setCreateBy(SecurityUtils.getUsername());
        newDefinition.setUpdateBy(SecurityUtils.getUsername());
        newDefinition.setCreateTime(LocalDateTime.now());
        newDefinition.setUpdateTime(LocalDateTime.now());
        // 处理版本号
        handleVersion(newDefinition);
        flowDefinitionMapper.insert(newDefinition);
        return newDefinition.getId();
    }

    @Override
    public byte[] exportFlowDefinition(Long id) {
        FlowDefinition flowDefinition = flowDefinitionMapper.selectById(id);
        if (flowDefinition == null) {
            throw new RuntimeException("流程定义不存在");
        }
        // 实际项目中应实现JSON格式的流程定义导出
        return flowDefinition.getFlowJson().getBytes();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importFlowDefinition(byte[] data) {
        String flowJson = new String(data);
        // 实际项目中应解析JSON并创建流程定义
        FlowDefinition flowDefinition = new FlowDefinition();
        flowDefinition.setName("导入的流程");
        flowDefinition.setCode("IMPORT_" + System.currentTimeMillis());
        flowDefinition.setFlowJson(flowJson);
        flowDefinition.setStatus(0);
        flowDefinition.setTenantId(SecurityUtils.getTenantId());
        flowDefinition.setCreateBy(SecurityUtils.getUsername());
        flowDefinition.setUpdateBy(SecurityUtils.getUsername());
        flowDefinition.setCreateTime(LocalDateTime.now());
        flowDefinition.setUpdateTime(LocalDateTime.now());
        handleVersion(flowDefinition);
        flowDefinitionMapper.insert(flowDefinition);
        return flowDefinition.getId();
    }

    /**
     * 处理版本号
     */
    private void handleVersion(FlowDefinition flowDefinition) {
        LambdaQueryWrapper<FlowDefinition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FlowDefinition::getCode, flowDefinition.getCode())
               .eq(FlowDefinition::getTenantId, flowDefinition.getTenantId());
        List<FlowDefinition> list = flowDefinitionMapper.selectList(wrapper);
        if (list.isEmpty()) {
            flowDefinition.setVersion(1);
        } else {
            int maxVersion = list.stream().mapToInt(FlowDefinition::getVersion).max().orElse(0);
            flowDefinition.setVersion(maxVersion + 1);
        }
    }
}