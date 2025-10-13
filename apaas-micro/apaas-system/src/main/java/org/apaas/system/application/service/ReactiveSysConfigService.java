package org.apaas.system.application.service;

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.system.domain.model.SysConfig;
import org.apaas.system.application.dto.SysConfigDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式参数配置服务接口
 * @author ivan
 */
public interface ReactiveSysConfigService extends ApplicationService<SysConfig, Long> {
    
    /**
     * 分页查询参数配置列表
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 参数配置列表
     */
    Flux<SysConfigDTO> getConfigPage(Pageable pageable, SysConfigDTO query);
    
    /**
     * 新增参数配置
     *
     * @param configDto 参数配置信息
     * @return 结果
     */
    Mono<Boolean> addConfig(SysConfigDTO configDto);
    
    /**
     * 修改参数配置
     *
     * @param configDto 参数配置信息
     * @return 结果
     */
    Mono<Boolean> updateConfig(SysConfigDTO configDto);
    
    /**
     * 删除参数配置
     *
     * @param id 参数配置ID
     * @return 结果
     */
    Mono<Boolean> deleteConfig(Long id);
    
    /**
     * 批量删除参数配置
     *
     * @param ids 参数配置ID数组
     * @return 结果
     */
    Mono<Boolean> batchDeleteConfig(List<Long> ids);
    
    /**
     * 修改参数配置状态
     *
     * @param id     参数配置ID
     * @param status 状态
     * @return 结果
     */
    Mono<Boolean> changeStatus(Long id, Integer status);
    
    /**
     * 根据参数编码查询参数配置
     *
     * @param code 参数编码
     * @return 参数配置信息
     */
    Mono<SysConfigDTO> getConfigByCode(String code);
    
    /**
     * 导出参数配置
     *
     * @param exchange 响应对象
     * @param query    查询条件
     * @return 结果
     */
    Mono<Void> exportExcel(ServerWebExchange exchange, SysConfigDTO query);
    
    /**
     * 导入参数配置
     *
     * @param fileData 文件数据
     * @return 结果
     */
    Mono<Boolean> importExcel(byte[] fileData);
}