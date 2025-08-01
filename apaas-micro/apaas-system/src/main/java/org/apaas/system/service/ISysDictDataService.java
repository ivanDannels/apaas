package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.system.domain.entity.SysDictData;

import java.util.List;

/**
 * 字典数据服务接口
 */
public interface ISysDictDataService extends IService<SysDictData> {

    /**
     * 分页查询字典数据
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 字典数据列表
     */
    Page<SysDictData> selectDictDataPage(Page<SysDictData> page, BasePageQuery query);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    String selectDictLabel(String dictType, String dictValue);

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    boolean insertDictData(SysDictData dictData);

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    boolean updateDictData(SysDictData dictData);

    /**
     * 批量删除字典数据信息
     *
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    boolean deleteDictDataByIds(Long[] dictCodes);
}