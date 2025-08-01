package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.core.utils.SecurityUtils;
import org.apaas.system.domain.dto.SysConfigDTO;
import org.apaas.system.domain.excel.SysConfigExcel;
import org.apaas.system.entity.SysConfig;
import org.apaas.system.mapper.SysConfigMapper;
import org.apaas.system.service.SysConfigService;
import org.apaas.system.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 参数配置Service实现类
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Autowired
    private SysConfigMapper configMapper;

    /**
     * 分页查询参数配置列表
     */
    @Override
    public Page<SysConfig> getConfigPage(Page<SysConfig> page, SysConfigDTO query) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(query.getId() != null, "id", query.getId());
        queryWrapper.like(query.getName() != null, "name", query.getName());
        queryWrapper.like(query.getConfigKey() != null, "config_key", query.getConfigKey());
        queryWrapper.like(query.getCode() != null, "code", query.getCode());
        queryWrapper.eq(query.getType() != null, "type", query.getType());
        queryWrapper.eq(query.getStatus() != null, "status", query.getStatus());
        queryWrapper.orderByDesc("create_time");
        return configMapper.selectPage(page, queryWrapper);
    }

    /**
     * 新增参数配置
     */
    @Override
    @Transactional
    public boolean addConfig(SysConfig config) {
        // 检查参数编码是否已存在
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", config.getCode());
        if (configMapper.selectCount(queryWrapper) > 0) {
            throw new RuntimeException("参数编码已存在");
        }

        // 设置租户ID
        config.setTenantId(SecurityUtils.getCurrentTenantId());
        // 设置创建人信息
        config.setCreateBy(SecurityUtils.getUsername());
        config.setCreateTime(LocalDateTime.now());
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(LocalDateTime.now());
        return configMapper.insert(config) > 0;
    }

    /**
     * 修改参数配置
     */
    @Override
    @Transactional
    public boolean updateConfig(SysConfig config) {
        // 检查参数编码是否已存在
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", config.getCode());
        queryWrapper.ne("id", config.getId());
        if (configMapper.selectCount(queryWrapper) > 0) {
            throw new RuntimeException("参数编码已存在");
        }

        // 设置更新人信息
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(LocalDateTime.now());
        return configMapper.updateById(config) > 0;
    }

    /**
     * 删除参数配置
     */
    @Override
    @Transactional
    public boolean deleteConfig(Long id) {
        return configMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除参数配置
     */
    @Override
    @Transactional
    public boolean batchDeleteConfig(Long[] ids) {
        return configMapper.deleteBatchIds(Arrays.asList(ids)) > 0;
    }

    /**
     * 修改参数配置状态
     */
    @Override
    @Transactional
    public boolean changeStatus(Long id, Integer status) {
        SysConfig config = new SysConfig();
        config.setId(id);
        config.setStatus(status);
        config.setUpdateBy(SecurityUtils.getUsername());
        config.setUpdateTime(LocalDateTime.now());
        return configMapper.updateById(config) > 0;
    }

    /**
     * 根据参数编码查询参数配置
     */
    @Override
    public SysConfig getConfigByCode(String code) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        queryWrapper.eq("tenant_id", SecurityUtils.getCurrentTenantId());
        return configMapper.selectOne(queryWrapper);
    }

    /**
     * 导出参数配置
     */
    @Override
    public void exportExcel(HttpServletResponse response, SysConfigDTO query) throws IOException {
        // 查询参数配置列表
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(query.getId() != null, "id", query.getId());
        queryWrapper.like(query.getName() != null, "name", query.getName());
        queryWrapper.like(query.getConfigKey() != null, "config_key", query.getConfigKey());
        queryWrapper.like(query.getCode() != null, "code", query.getCode());
        queryWrapper.eq(query.getType() != null, "type", query.getType());
        queryWrapper.eq(query.getStatus() != null, "status", query.getStatus());
        queryWrapper.eq("tenant_id", SecurityUtils.getCurrentTenantId());
        List<SysConfig> configList = configMapper.selectList(queryWrapper);

        // 转换为Excel实体列表
        List<SysConfigExcel> excelList = new ArrayList<>();
        for (SysConfig config : configList) {
            SysConfigExcel excel = new SysConfigExcel();
            excel.setName(config.getName());
            excel.setConfigKey(config.getConfigKey());
            excel.setCode(config.getCode());
            excel.setValue(config.getValue());
            excel.setType(config.getType());
            excel.setStatus(config.getStatus());
            excel.setDescription(config.getDescription());
            excelList.add(excel);
        }

        // 导出Excel
        ExcelUtils.exportExcel(response, "参数配置列表", "参数配置", excelList, SysConfigExcel.class);
    }

    /**
     * 导入参数配置
     */
    @Override
    @Transactional
    public boolean importExcel(MultipartFile file) throws IOException {
        // 读取Excel文件
        List<SysConfigExcel> excelList = ExcelUtils.importExcel(file, SysConfigExcel.class);

        // 转换为实体并保存
        for (SysConfigExcel excel : excelList) {
            SysConfig config = new SysConfig();
            config.setName(excel.getName());
            config.setConfigKey(excel.getConfigKey());
            config.setCode(excel.getCode());
            config.setValue(excel.getValue());
            config.setType(excel.getType());
            config.setStatus(excel.getStatus());
            config.setDescription(excel.getDescription());
            config.setTenantId(SecurityUtils.getCurrentTenantId());
            config.setCreateBy(SecurityUtils.getUsername());
            config.setCreateTime(LocalDateTime.now());
            config.setUpdateBy(SecurityUtils.getUsername());
            config.setUpdateTime(LocalDateTime.now());

            // 检查参数编码是否已存在
            QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", config.getCode());
            queryWrapper.eq("tenant_id", SecurityUtils.getCurrentTenantId());
            if (configMapper.selectCount(queryWrapper) > 0) {
                // 更新已存在的参数配置
                SysConfig existingConfig = configMapper.selectOne(queryWrapper);
                config.setId(existingConfig.getId());
                configMapper.updateById(config);
            } else {
                // 新增参数配置
                configMapper.insert(config);
            }
        }

        return true;
    }
}