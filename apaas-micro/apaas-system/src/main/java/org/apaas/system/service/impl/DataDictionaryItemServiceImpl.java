package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.system.domain.dto.DataDictionaryItemDTO;
import org.apaas.system.domain.excel.DataDictionaryItemExcel;
import org.apaas.system.entity.DataDictionaryItem;
import org.apaas.system.mapper.DataDictionaryItemMapper;
import org.apaas.system.service.DataDictionaryItemService;
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
 * 数据字典项服务实现类
 */
@Service
public class DataDictionaryItemServiceImpl extends ServiceImpl<DataDictionaryItemMapper, DataDictionaryItem> implements DataDictionaryItemService {

    @Autowired
    private DataDictionaryItemMapper dataDictionaryItemMapper;

    @Override
    public IPage<DataDictionaryItem> selectPage(DataDictionaryItemDTO query) {
        Page<DataDictionaryItem> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<DataDictionaryItem> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted", 0);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        if (query.getDictionaryId() != null) {
            wrapper.eq("dictionary_id", query.getDictionaryId());
        }
        if (query.getName() != null) {
            wrapper.like("name", query.getName());
        }
        if (query.getCode() != null) {
            wrapper.like("code", query.getCode());
        }
        if (query.getStatus() != null) {
            wrapper.eq("status", query.getStatus());
        }
        wrapper.orderByAsc("sort");
        return dataDictionaryItemMapper.selectPage(page, wrapper);
    }

    @Override
    public List<DataDictionaryItem> selectByDictionaryId(Long dictionaryId) {
        QueryWrapper<DataDictionaryItem> wrapper = new QueryWrapper<>();
        wrapper.eq("dictionary_id", dictionaryId);
        wrapper.eq("deleted", 0);
        wrapper.eq("status", 0);
        wrapper.eq("tenant_id", SecurityUtils.getTenantId());
        wrapper.orderByAsc("sort");
        return dataDictionaryItemMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public boolean create(DataDictionaryItem dataDictionaryItem) {
        dataDictionaryItem.setTenantId(SecurityUtils.getTenantId());
        dataDictionaryItem.setCreateBy(SecurityUtils.getUsername());
        dataDictionaryItem.setCreateTime(LocalDateTime.now());
        dataDictionaryItem.setUpdateBy(SecurityUtils.getUsername());
        dataDictionaryItem.setUpdateTime(LocalDateTime.now());
        dataDictionaryItem.setDeleted(0);
        return save(dataDictionaryItem);
    }

    @Override
    @Transactional
    public boolean update(DataDictionaryItem dataDictionaryItem) {
        dataDictionaryItem.setUpdateBy(SecurityUtils.getUsername());
        dataDictionaryItem.setUpdateTime(LocalDateTime.now());
        return updateById(dataDictionaryItem);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        DataDictionaryItem dataDictionaryItem = new DataDictionaryItem();
        dataDictionaryItem.setId(id);
        dataDictionaryItem.setDeleted(1);
        dataDictionaryItem.setUpdateBy(SecurityUtils.getUsername());
        dataDictionaryItem.setUpdateTime(LocalDateTime.now());
        return updateById(dataDictionaryItem);
    }

    @Override
    @Transactional
    public boolean changeStatus(Long id, Integer status) {
        DataDictionaryItem dataDictionaryItem = new DataDictionaryItem();
        dataDictionaryItem.setId(id);
        dataDictionaryItem.setStatus(status);
        dataDictionaryItem.setUpdateBy(SecurityUtils.getUsername());
        dataDictionaryItem.setUpdateTime(LocalDateTime.now());
        return updateById(dataDictionaryItem);
    }

    @Override
    public void exportExcel(HttpServletResponse response, DataDictionaryItemDTO query) throws IOException {
        // 查询数据字典项列表
        List<DataDictionaryItem> itemList;
        if (query == null) {
            QueryWrapper<DataDictionaryItem> wrapper = new QueryWrapper<>();
            wrapper.eq("deleted", 0);
            wrapper.eq("tenant_id", SecurityUtils.getTenantId());
            itemList = dataDictionaryItemMapper.selectList(wrapper);
        } else {
            IPage<DataDictionaryItem> page = selectPage(query);
            itemList = page.getRecords();
        }

        // 转换为Excel实体
        List<DataDictionaryItemExcel> excelList = new ArrayList<>();
        for (DataDictionaryItem item : itemList) {
            DataDictionaryItemExcel excel = new DataDictionaryItemExcel();
            excel.setCode(item.getCode());
            excel.setName(item.getName());
            excel.setValue(item.getValue());
            excel.setSort(item.getSort());
            excel.setStatus(item.getStatus());
            excel.setDescription(item.getDescription());
            excelList.add(excel);
        }

        // 导出Excel
        ExcelUtils.exportExcel(response, excelList, DataDictionaryItemExcel.class, "数据字典项");
    }

    @Override
    @Transactional
    public boolean importExcel(Long dictionaryId, MultipartFile file) throws IOException {
        // 导入Excel数据
        List<DataDictionaryItemExcel> excelList = ExcelUtils.importExcel(file, DataDictionaryItemExcel.class);

        // 保存数据字典项
        for (DataDictionaryItemExcel excel : excelList) {
            DataDictionaryItem item = new DataDictionaryItem();
            item.setDictionaryId(dictionaryId);
            item.setCode(excel.getCode());
            item.setName(excel.getName());
            item.setValue(excel.getValue());
            item.setSort(excel.getSort());
            item.setStatus(excel.getStatus());
            item.setDescription(excel.getDescription());
            item.setTenantId(SecurityUtils.getTenantId());
            item.setCreateBy(SecurityUtils.getUsername());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateBy(SecurityUtils.getUsername());
            item.setUpdateTime(LocalDateTime.now());
            item.setDeleted(0);
            dataDictionaryItemMapper.insert(item);
        }

        return true;
    }
}