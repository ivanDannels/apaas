package org.apaas.form.engine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.form.engine.entity.FormField;

import java.util.List;

/**
 * 表单字段服务接口
 */
public interface FormFieldService extends IService<FormField> {
    /**
     * 分页查询表单字段
     *
     * @param formId 表单ID
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    IPage<FormField> selectPage(Long formId, Integer pageNum, Integer pageSize);

    /**
     * 根据表单ID查询字段列表
     *
     * @param formId 表单ID
     * @return 字段列表
     */
    List<FormField> selectByFormId(Long formId);

    /**
     * 根据表单ID和字段类型查询字段列表
     *
     * @param formId 表单ID
     * @param type 字段类型
     * @return 字段列表
     */
    List<FormField> selectByFormIdAndType(Long formId, Integer type);

    /**
     * 根据表单ID和分组名称查询字段列表
     *
     * @param formId 表单ID
     * @param groupName 分组名称
     * @return 字段列表
     */
    List<FormField> selectByFormIdAndGroupName(Long formId, String groupName);

    /**
     * 创建表单字段
     *
     * @param formField 表单字段
     * @return 是否成功
     */
    boolean create(FormField formField);

    /**
     * 更新表单字段
     *
     * @param formField 表单字段
     * @return 是否成功
     */
    boolean update(FormField formField);

    /**
     * 删除表单字段
     *
     * @param id 字段ID
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 批量创建表单字段
     *
     * @param formId 表单ID
     * @param formFields 表单字段列表
     * @return 是否成功
     */
    boolean batchCreate(Long formId, List<FormField> formFields);
}