package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.system.domain.dto.SysConfigDTO;
import org.apaas.system.entity.SysConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 参数配置Service接口
 */
public interface SysConfigService extends IService<SysConfig> {
    /**
     * 分页查询参数配置列表
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 参数配置列表
     */
    Page<SysConfig> getConfigPage(Page<SysConfig> page, SysConfigDTO query);

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    boolean addConfig(SysConfig config);

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    boolean updateConfig(SysConfig config);

    /**
     * 删除参数配置
     *
     * @param id 参数配置ID
     * @return 结果
     */
    boolean deleteConfig(Long id);

    /**
     * 批量删除参数配置
     *
     * @param ids 参数配置ID数组
     * @return 结果
     */
    boolean batchDeleteConfig(Long[] ids);

    /**
     * 修改参数配置状态
     *
     * @param id 参数配置ID
     * @param status 状态
     * @return 结果
     */
    boolean changeStatus(Long id, Integer status);

    /**
     * 根据参数编码查询参数配置
     *
     * @param code 参数编码
     * @return 参数配置信息
     */
    SysConfig getConfigByCode(String code);

    /**
     * 导出参数配置
     *
     * @param response 响应对象
     * @param query 查询条件
     * @throws IOException IO异常
     */
    void exportExcel(HttpServletResponse response, SysConfigDTO query) throws IOException;

    /**
     * 导入参数配置
     *
     * @param file Excel文件
     * @return 结果
     * @throws IOException IO异常
     */
    boolean importExcel(MultipartFile file) throws IOException;
}