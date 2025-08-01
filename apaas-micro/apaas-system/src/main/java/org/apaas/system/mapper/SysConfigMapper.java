package org.apaas.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.system.domain.entity.SysConfig;
import org.apache.ibatis.annotations.Param;

/**
 * 参数配置 数据层
 */
public interface SysConfigMapper extends BaseMapper<SysConfig> {

    /**
     * 分页查询参数配置列表
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 参数配置集合
     */
    Page<SysConfig> selectConfigPage(Page<SysConfig> page, @Param("query") BasePageQuery query);

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    SysConfig selectConfigById(Long configId);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    SysConfig selectConfigByKey(String configKey);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    int insertConfig(SysConfig config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    int updateConfig(SysConfig config);

    /**
     * 删除参数配置
     *
     * @param configId 参数配置ID
     * @return 结果
     */
    int deleteConfigById(Long configId);

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    int deleteConfigByIds(Long[] configIds);

    /**
     * 校验参数键名是否唯一
     *
     * @param configKey 参数键名
     * @return 结果
     */
    SysConfig checkConfigKeyUnique(String configKey);
}