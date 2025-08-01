package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionary;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 数据字典服务接口
 */
public interface DataDictionaryService extends IService<DataDictionary> {
    /**
     * 分页查询数据字典
     */
    IPage<DataDictionary> selectPage(DataDictionaryDTO query);

    /**
     * 创建数据字典
     */
    boolean create(DataDictionary dataDictionary);

    /**
     * 更新数据字典
     */
    boolean update(DataDictionary dataDictionary);

    /**
     * 删除数据字典
     */
    boolean delete(Long id);

    /**
     * 启用/停用数据字典
     */
    boolean changeStatus(Long id, Integer status);

    /**
     * 导出数据字典
     */
    void exportExcel(HttpServletResponse response, DataDictionaryDTO query) throws IOException;

    /**
     * 导入数据字典
     */
    boolean importExcel(MultipartFile file) throws IOException;
}