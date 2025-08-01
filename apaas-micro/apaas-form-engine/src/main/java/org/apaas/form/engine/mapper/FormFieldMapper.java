package org.apaas.form.engine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apaas.form.engine.entity.FormField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表单字段Mapper接口
 */
@Mapper
public interface FormFieldMapper extends BaseMapper<FormField> {
    /**
     * 根据表单ID查询字段列表
     *
     * @param formId 表单ID
     * @return 字段列表
     */
    List<FormField> selectByFormId(@Param("formId") Long formId);

    /**
     * 根据表单ID和字段类型查询字段列表
     *
     * @param formId 表单ID
     * @param type 字段类型
     * @return 字段列表
     */
    List<FormField> selectByFormIdAndType(@Param("formId") Long formId, @Param("type") Integer type);

    /**
     * 根据表单ID和分组名称查询字段列表
     *
     * @param formId 表单ID
     * @param groupName 分组名称
     * @return 字段列表
     */
    List<FormField> selectByFormIdAndGroupName(@Param("formId") Long formId, @Param("groupName") String groupName);
}