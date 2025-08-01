package org.apaas.flow.engine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import org.apache.ibatis.annotations.Param;

/**
 * 流程定义Mapper接口
 */
public interface FlowDefinitionMapper extends BaseMapper<FlowDefinition> {
    /**
     * 分页查询流程定义
     */
    IPage<FlowDefinition> selectPage(Page<FlowDefinition> page, @Param("query") FlowDefinitionDTO query);

    /**
     * 更新流程定义的默认版本状态
     */
    int updateIsDefaultByCode(@Param("code") String code, @Param("isDefault") Integer isDefault);
}