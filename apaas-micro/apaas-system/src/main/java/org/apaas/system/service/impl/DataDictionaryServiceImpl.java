package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.domain.excel.DataDictionaryExcel;
import org.apaas.system.entity.DataDictionary;
import org.apaas.system.entity.DataDictionaryItem;
import org.apaas.system.mapper.DataDictionaryMapper;
import org.apaas.system.mapper.DataDictionaryItemMapper;
import org.apaas.system.service.DataDictionaryService;
import org.apaas.system.utils.ExcelUtils;
import org.apaas.core.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典服务实现类
 */
@Service
public class DataDictionaryServiceImpl extends ServiceImpl<DataDictionaryMapper, DataDictionary> implements DataDictionaryService {

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    @Autowired
    private DataDictionaryItemMapper dataDictionaryItemMapper;

    @Override
    public IPage<DataDictionary> selectPage(DataDictionaryDTO query) {
        Page<DataDictionary> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<DataDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        if (query.getName() != null) {
            wrapper.like("name", query.getName());
        }
        if (query.getType() != null) {
            wrapper.eq("type", query.getType());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        wrapper.orderByDesc("create_time");
        return dataDictionaryMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional
    public boolean create(DataDictionary dataDictionary) {
        dataDictionary.setTenantId(SecurityUtils.getTenantId());
        dataDictionary.setCreateBy(SecurityUtils.getUsername());
        dataDictionary.setCreateTime(LocalDateTime.now());
        dataDictionary.setUpdateBy(SecurityUtils.getUsername());
        dataDictionary.setUpdateTime(LocalDateTime.now());
        dataDictionary.setDeleted(0);
        return save(dataDictionary);
    }

    @Override
    @Transactional
    public boolean update(DataDictionary dataDictionary) {
        dataDictionary.setUpdateBy(SecurityUtils.getUsername());
        dataDictionary.setUpdateTime(LocalDateTime.now());
        return updateById(dataDictionary);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setId(id);
        dataDictionary.setDeleted(1);
        dataDictionary.setUpdateBy(SecurityUtils.getUsername());
        dataDictionary.setUpdateTime(LocalDateTime.now());
        // 删除字典项
        QueryWrapper<DataDictionaryItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("dictionary_id", id);
        itemWrapper.eq("tenant_id", SecurityUtils.getTenantId());
        DataDictionaryItem item = new DataDictionaryItem();
        item.setDeleted(1);
        item.setUpdateBy(SecurityUtils.getUsername());
        item.setUpdateTime(LocalDateTime.now());
        dataDictionaryItemMapper.update(item, itemWrapper);
        return updateById(dataDictionary);
    }

    @Override
    @Transactional
    public boolean changeStatus(Long id, Integer status) {
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setId(id);
        dataDictionary.setStatus(status);
        dataDictionary.setUpdateBy(SecurityUtils.getUsername());
        dataDictionary.setUpdateTime(LocalDateTime.now());
        return updateById(dataDictionary);
    }

    @Override
    public void exportExcel(HttpServletResponse response, DataDictionaryDTO query) throws IOException {
        // 查询数据字典列表
        List<DataDictionary> dataDictionaryList;
        if (query == null) {
            QueryWrapper<DataDictionary> wrapper = new QueryWrapper<>();
            wrapper.eq("deleted", 0);
            wrapper.eq("tenant_id", SecurityUtils.getTenantId());
            dataDictionaryList = dataDictionaryMapper.selectList(wrapper);
        } else {
            IPage<DataDictionary> page = selectPage(query);
            dataDictionaryList = page.getRecords();
        }

        // 转换为Excel实体
        List<DataDictionaryExcel> excelList = new ArrayList<>();
        for (DataDictionary dataDictionary : dataDictionaryList) {
            DataDictionaryExcel excel = new DataDictionaryExcel();
            excel.setName(dataDictionary.getName());
            excel.setCode(dataDictionary.getCode());
            excel.setType(dataDictionary.getType());
            excel.setStatus(dataDictionary.getStatus());
            excel.setDescription(dataDictionary.getDescription());
            excelList.add(excel);
        }

        // 导出Excel
        ExcelUtils.exportExcel(response, excelList, DataDictionaryExcel.class, "数据字典");
    }

    @Override
    @Transactional
    public boolean importExcel(MultipartFile file) throws IOException {
        // 导入Excel数据
        List<DataDictionaryExcel> excelList = ExcelUtils.importExcel(file, DataDictionaryExcel.class);

        // 保存数据字典
        for (DataDictionaryExcel excel : excelList) {
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setName(excel.getName());
            dataDictionary.setCode(excel.getCode());
            dataDictionary.setType(excel.getType());
            dataDictionary.setStatus(excel.getStatus());
            dataDictionary.setDescription(excel.getDescription());
            dataDictionary.setTenantId(SecurityUtils.getTenantId());
            dataDictionary.setCreateBy(SecurityUtils.getUsername());
            dataDictionary.setCreateTime(LocalDateTime.now());
            dataDictionary.setUpdateBy(SecurityUtils.getUsername());
            dataDictionary.setUpdateTime(LocalDateTime.now());
            dataDictionary.setDeleted(0);
            dataDictionaryMapper.insert(dataDictionary);
        }

        return true;
    }
}