package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.system.domain.dto.DataDictionaryItemDTO;
import org.apaas.system.entity.DataDictionaryItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 数据字典项服务接口
 */
public interface DataDictionaryItemService extends IService<DataDictionaryItem> {
    /**
     * 分页查询数据字典项
     */
    IPage<DataDictionaryItem> selectPage(DataDictionaryItemDTO query);

    /**
     * 根据字典ID查询字典项列表
     */
    List<DataDictionaryItem> selectByDictionaryId(Long dictionaryId);

    /**
     * 创建数据字典项
     */
    boolean create(DataDictionaryItem dataDictionaryItem);

    /**
     * 更新数据字典项
     */
    boolean update(DataDictionaryItem dataDictionaryItem);

    /**
     * 删除数据字典项
     */
    boolean delete(Long id);

    /**
     * 启用/停用数据字典项
     */
    boolean changeStatus(Long id, Integer status);

    /**
     * 导出数据字典项
     */
    void exportExcel(HttpServletResponse response, DataDictionaryItemDTO query) throws IOException;

    /**
     * 导入数据字典项
     */
    boolean importExcel(Long dictionaryId, MultipartFile file) throws IOException;
}