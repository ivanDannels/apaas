package org.apaas.form.engine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.form.engine.domain.dto.FormDefinitionDTO;
import org.apaas.form.engine.entity.FormDefinition;
import org.apache.ibatis.annotations.Param;

/**
 * 表单定义Mapper接口
 */
public interface FormDefinitionMapper extends BaseMapper<FormDefinition> {
    /**
     * 分页查询表单定义
     */
    IPage<FormDefinition> selectPage(Page<FormDefinition> page, @Param("query") FormDefinitionDTO query);

    /**
     * 更新表单定义的默认版本状态
     */
    int updateIsDefaultByCode(@Param("code") String code, @Param("isDefault") Integer isDefault);
}