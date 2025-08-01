package org.apaas.form.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.form.engine.domain.dto.FormDefinitionDTO;
import org.apaas.form.engine.entity.FormDefinition;
import org.apaas.form.engine.mapper.FormDefinitionMapper;
import org.apaas.form.engine.service.FormDefinitionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表单定义服务实现
 */
@Service
public class FormDefinitionServiceImpl extends ServiceImpl<FormDefinitionMapper, FormDefinition> implements FormDefinitionService {

    @Resource
    private FormDefinitionMapper formDefinitionMapper;

    @Override
    public IPage<FormDefinition> selectFormDefinitionPage(FormDefinitionDTO query) {
        Page<FormDefinition> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<FormDefinition> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(query.getName() != null, FormDefinition::getName, query.getName())
               .eq(query.getType() != null, FormDefinition::getType, query.getType())
               .eq(query.getStatus() != null, FormDefinition::getStatus, query.getStatus())
               .eq(FormDefinition::getTenantId, SecurityUtils.getTenantId())
               .orderByDesc(FormDefinition::getCreateTime);
        return formDefinitionMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveFormDefinition(FormDefinition formDefinition) {
        // 设置租户ID
        formDefinition.setTenantId(SecurityUtils.getTenantId());
        // 设置创建人、更新人
        String username = SecurityUtils.getUsername();
        formDefinition.setCreateBy(username);
        formDefinition.setUpdateBy(username);
        // 设置创建时间、更新时间
        LocalDateTime now = LocalDateTime.now();
        formDefinition.setCreateTime(now);
        formDefinition.setUpdateTime(now);
        // 初始状态为草稿
        formDefinition.setStatus(0);
        // 处理版本号
        handleVersion(formDefinition);
        // 保存表单定义
        formDefinitionMapper.insert(formDefinition);
        return formDefinition.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFormDefinition(FormDefinition formDefinition) {
        // 设置更新人、更新时间
        formDefinition.setUpdateBy(SecurityUtils.getUsername());
        formDefinition.setUpdateTime(LocalDateTime.now());
        // 如果状态为已发布，则不允许修改
        FormDefinition oldDefinition = formDefinitionMapper.selectById(formDefinition.getId());
        if (oldDefinition.getStatus() == 1) {
            throw new RuntimeException("已发布的表单定义不允许修改");
        }
        return formDefinitionMapper.updateById(formDefinition) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFormDefinitions(Long[] ids) {
        // 检查是否有已发布的表单定义
        List<FormDefinition> list = formDefinitionMapper.selectBatchIds(Arrays.asList(ids));
        boolean hasPublished = list.stream().anyMatch(fd -> fd.getStatus() == 1);
        if (hasPublished) {
            throw new RuntimeException("包含已发布的表单定义，不允许删除");
        }
        return formDefinitionMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishFormDefinition(Long id) {
        FormDefinition formDefinition = formDefinitionMapper.selectById(id);
        if (formDefinition == null) {
            throw new RuntimeException("表单定义不存在");
        }
        // 设置状态为已发布
        formDefinition.setStatus(1);
        formDefinition.setUpdateBy(SecurityUtils.getUsername());
        formDefinition.setUpdateTime(LocalDateTime.now());
        // 如果设为默认版本，则更新其他版本为非默认
        if (formDefinition.getIsDefault()) {
            formDefinitionMapper.updateIsDefaultByCode(formDefinition.getCode(), 0);
        }
        return formDefinitionMapper.updateById(formDefinition) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableFormDefinition(Long id) {
        FormDefinition formDefinition = formDefinitionMapper.selectById(id);
        if (formDefinition == null) {
            throw new RuntimeException("表单定义不存在");
        }
        // 设置状态为已停用
        formDefinition.setStatus(2);
        formDefinition.setUpdateBy(SecurityUtils.getUsername());
        formDefinition.setUpdateTime(LocalDateTime.now());
        return formDefinitionMapper.updateById(formDefinition) > 0;
    }

    @Override
    public List<FormDefinition> getVersionsByCode(String code) {
        LambdaQueryWrapper<FormDefinition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormDefinition::getCode, code)
               .eq(FormDefinition::getTenantId, SecurityUtils.getTenantId())
               .orderByDesc(FormDefinition::getVersion);
        return formDefinitionMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long copyFormDefinition(Long id, String newName) {
        FormDefinition source = formDefinitionMapper.selectById(id);
        if (source == null) {
            throw new RuntimeException("表单定义不存在");
        }
        // 创建新的表单定义
        FormDefinition newDefinition = new FormDefinition();
        newDefinition.setName(newName);
        newDefinition.setCode(source.getCode());
        newDefinition.setType(source.getType());
        newDefinition.setConfigJson(source.getConfigJson());
        newDefinition.setItemsJson(source.getItemsJson());
        newDefinition.setDataSourceId(source.getDataSourceId());
        newDefinition.setFlowId(source.getFlowId());
        newDefinition.setStatus(0); // 草稿状态
        newDefinition.setIsDefault(false);
        newDefinition.setTenantId(SecurityUtils.getTenantId());
        newDefinition.setCreateBy(SecurityUtils.getUsername());
        newDefinition.setUpdateBy(SecurityUtils.getUsername());
        newDefinition.setCreateTime(LocalDateTime.now());
        newDefinition.setUpdateTime(LocalDateTime.now());
        // 处理版本号
        handleVersion(newDefinition);
        formDefinitionMapper.insert(newDefinition);
        return newDefinition.getId();
    }

    @Override
    public byte[] exportFormDefinition(Long id) {
        FormDefinition formDefinition = formDefinitionMapper.selectById(id);
        if (formDefinition == null) {
            throw new RuntimeException("表单定义不存在");
        }
        // 实际项目中应实现JSON格式的表单定义导出
        return formDefinition.getItemsJson().getBytes();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long importFormDefinition(byte[] data) {
        String itemsJson = new String(data);
        // 实际项目中应解析JSON并创建表单定义
        FormDefinition formDefinition = new FormDefinition();
        formDefinition.setName("导入的表单");
        formDefinition.setCode("IMPORT_" + System.currentTimeMillis());
        formDefinition.setItemsJson(itemsJson);
        formDefinition.setStatus(0);
        formDefinition.setTenantId(SecurityUtils.getTenantId());
        formDefinition.setCreateBy(SecurityUtils.getUsername());
        formDefinition.setUpdateBy(SecurityUtils.getUsername());
        formDefinition.setCreateTime(LocalDateTime.now());
        formDefinition.setUpdateTime(LocalDateTime.now());
        handleVersion(formDefinition);
        formDefinitionMapper.insert(formDefinition);
        return formDefinition.getId();
    }

    /**
     * 处理版本号
     */
    private void handleVersion(FormDefinition formDefinition) {
        LambdaQueryWrapper<FormDefinition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FormDefinition::getCode, formDefinition.getCode())
               .eq(FormDefinition::getTenantId, formDefinition.getTenantId());
        List<FormDefinition> list = formDefinitionMapper.selectList(wrapper);
        if (list.isEmpty()) {
            formDefinition.setVersion(1);
        } else {
            int maxVersion = list.stream().mapToInt(FormDefinition::getVersion).max().orElse(0);
            formDefinition.setVersion(maxVersion + 1);
        }
    }
}