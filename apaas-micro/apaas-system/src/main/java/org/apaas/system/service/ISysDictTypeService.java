package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.system.domain.entity.SysDictType;

import java.util.List;

/**
 * 字典类型服务接口
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    /**
     * 分页查询字典类型
     *
     * @param page 分页参数
     * @param query 查询条件
     * @return 字典类型列表
     */
    Page<SysDictType> selectDictTypePage(Page<SysDictType> page, BasePageQuery query);

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    List<SysDictType> selectDictTypeByType(String dictType);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    boolean checkDictTypeUnique(SysDictType dictType);

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    boolean insertDictType(SysDictType dictType);

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    boolean updateDictType(SysDictType dictType);

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    boolean deleteDictTypeByIds(Long[] dictIds);

    /**
     * 加载参数缓存数据
     */
    void loadingDictCache();

    /**
     * 清空字典缓存数据
     */
    void clearDictCache();

    /**
     * 重置字典缓存数据
     */
    void resetDictCache();
}