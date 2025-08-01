package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apaas.core.constant.Constants;
import org.apaas.core.utils.RedisUtils;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.system.domain.entity.SysDictData;
import org.apaas.system.mapper.SysDictDataMapper;
import org.apaas.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典数据服务实现
 */
@Service
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    private final SysDictDataMapper dictDataMapper;
    private final RedisUtils redisUtils;

    /**
     * 分页查询字典数据
     */
    @Override
    public Page<SysDictData> selectDictDataPage(Page<SysDictData> page, BasePageQuery query) {
        return dictDataMapper.selectDictDataPage(page, query);
    }

    /**
     * 根据字典类型查询字典数据
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        List<SysDictData> dictDatas = redisUtils.getCacheObject(getCacheKey(dictType));
        if (dictDatas != null && !dictDatas.isEmpty()) {
            return dictDatas;
        }
        dictDatas = dictDataMapper.selectDictDataByType(dictType);
        if (dictDatas != null && !dictDatas.isEmpty()) {
            redisUtils.setCacheObject(getCacheKey(dictType), dictDatas);
            return dictDatas;
        }
        return dictDatas;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 新增保存字典数据信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertDictData(SysDictData dictData) {
        int row = dictDataMapper.insertDictData(dictData);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictData.getDictType());
            redisUtils.setCacheObject(getCacheKey(dictData.getDictType()), dictDatas);
        }
        return row > 0;
    }

    /**
     * 修改保存字典数据信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDictData(SysDictData dictData) {
        int row = dictDataMapper.updateDictData(dictData);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictData.getDictType());
            redisUtils.setCacheObject(getCacheKey(dictData.getDictType()), dictDatas);
        }
        return row > 0;
    }

    /**
     * 批量删除字典数据信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDictDataByIds(Long[] dictCodes) {
        for (Long dictCode : dictCodes) {
            SysDictData data = dictDataMapper.selectDictDataById(dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            redisUtils.setCacheObject(getCacheKey(data.getDictType()), dictDatas);
        }
        return true;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return Constants.SYS_DICT_KEY + configKey;
    }
}