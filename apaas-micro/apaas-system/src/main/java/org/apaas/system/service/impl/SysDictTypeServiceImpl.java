package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apaas.core.constant.Constants;
import org.apaas.core.exception.ServiceException;
import org.apaas.core.utils.RedisUtils;
import org.apaas.core.utils.StringUtils;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.system.domain.entity.SysDictData;
import org.apaas.system.domain.entity.SysDictType;
import org.apaas.system.mapper.SysDictDataMapper;
import org.apaas.system.mapper.SysDictTypeMapper;
import org.apaas.system.service.ISysDictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 字典类型服务实现
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    private final SysDictTypeMapper dictTypeMapper;
    private final SysDictDataMapper dictDataMapper;
    private final RedisUtils redisUtils;

    /**
     * 分页查询字典类型
     */
    @Override
    public Page<SysDictType> selectDictTypePage(Page<SysDictType> page, BasePageQuery query) {
        return dictTypeMapper.selectDictTypePage(page, query);
    }

    /**
     * 根据字典类型查询字典数据
     */
    @Override
    public List<SysDictType> selectDictTypeByType(String dictType) {
        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictType::getDictType, dictType);
        return list(wrapper);
    }

    /**
     * 校验字典类型称是否唯一
     */
    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {
        Long dictId = StringUtils.isNull(dictType.getId()) ? -1L : dictType.getId();
        SysDictType info = dictTypeMapper.checkDictTypeUnique(dictType.getDictType());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != dictId.longValue()) {
            return false;
        }
        return true;
    }

    /**
     * 新增保存字典类型信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertDictType(SysDictType dictType) {
        int row = dictTypeMapper.insertDictType(dictType);
        if (row > 0) {
            redisUtils.setCacheObject(getCacheKey(dictType.getDictType()), null);
        }
        return row > 0;
    }

    /**
     * 修改保存字典类型信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDictType(SysDictType dictType) {
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        int row = dictTypeMapper.updateDictType(dictType);
        if (row > 0) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
            redisUtils.setCacheObject(getCacheKey(dictType.getDictType()), dictDatas);
        }
        return row > 0;
    }

    /**
     * 批量删除字典类型信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDictTypeByIds(Long[] dictIds) {
        for (Long dictId : dictIds) {
            SysDictType dictType = selectById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
            redisUtils.deleteObject(getCacheKey(dictType.getDictType()));
        }
        int rows = dictTypeMapper.deleteDictTypeByIds(dictIds);
        return rows > 0;
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingDictCache() {
        List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
        for (SysDictType dictType : dictTypeList) {
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dictType.getDictType());
            redisUtils.setCacheObject(getCacheKey(dictType.getDictType()), dictDatas);
        }
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache() {
        redisUtils.deleteKeys(Constants.SYS_DICT_KEY + "*");
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
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