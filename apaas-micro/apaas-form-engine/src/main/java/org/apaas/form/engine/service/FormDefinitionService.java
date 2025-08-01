package org.apaas.form.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.form.engine.domain.dto.FormDefinitionDTO;
import org.apaas.form.engine.entity.FormDefinition;
import java.util.List;

/**
 * 表单定义服务接口
 */
public interface FormDefinitionService extends IService<FormDefinition> {
    /**
     * 分页查询表单定义
     */
    Page<FormDefinition> selectFormDefinitionPage(FormDefinitionDTO query);

    /**
     * 保存表单定义
     */
    Long saveFormDefinition(FormDefinition formDefinition);

    /**
     * 更新表单定义
     */
    boolean updateFormDefinition(FormDefinition formDefinition);

    /**
     * 删除表单定义
     */
    boolean deleteFormDefinitions(Long[] ids);

    /**
     * 发布表单定义
     */
    boolean publishFormDefinition(Long id);

    /**
     * 停用表单定义
     */
    boolean disableFormDefinition(Long id);

    /**
     * 根据表单编码获取所有版本
     */
    List<FormDefinition> getVersionsByCode(String code);

    /**
     * 复制表单定义
     */
    Long copyFormDefinition(Long id, String newName);

    /**
     * 导出表单定义
     */
    byte[] exportFormDefinition(Long id);

    /**
     * 导入表单定义
     */
    Long importFormDefinition(byte[] data);
}