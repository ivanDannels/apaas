package org.apaas.flow.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.flow.engine.domain.dto.FlowDefinitionDTO;
import org.apaas.flow.engine.entity.FlowDefinition;
import java.util.List;

/**
 * 流程定义服务接口
 */
public interface FlowDefinitionService extends IService<FlowDefinition> {
    /**
     * 分页查询流程定义
     */
    Page<FlowDefinition> selectFlowDefinitionPage(FlowDefinitionDTO query);

    /**
     * 保存流程定义
     */
    Long saveFlowDefinition(FlowDefinition flowDefinition);

    /**
     * 更新流程定义
     */
    boolean updateFlowDefinition(FlowDefinition flowDefinition);

    /**
     * 删除流程定义
     */
    boolean deleteFlowDefinitions(Long[] ids);

    /**
     * 部署流程定义
     */
    boolean deployFlowDefinition(Long id);

    /**
     * 停用流程定义
     */
    boolean disableFlowDefinition(Long id);

    /**
     * 根据流程编码获取所有版本
     */
    List<FlowDefinition> getVersionsByCode(String code);

    /**
     * 复制流程定义
     */
    Long copyFlowDefinition(Long id, String newName);

    /**
     * 导出流程定义
     */
    byte[] exportFlowDefinition(Long id);

    /**
     * 导入流程定义
     */
    Long importFlowDefinition(byte[] data);
}