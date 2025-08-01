package org.apaas.form.engine.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.form.engine.entity.FormField;
import org.apaas.form.engine.mapper.FormFieldMapper;
import org.apaas.form.engine.service.FormFieldService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 表单字段服务实现类
 */
@Service
public class FormFieldServiceImpl extends ServiceImpl<FormFieldMapper, FormField> implements FormFieldService {

    @Override
    public IPage<FormField> selectPage(Long formId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<FormField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FormField::getFormId, formId);
        queryWrapper.orderByAsc(FormField::getSort);
        return page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @Override
    public List<FormField> selectByFormId(Long formId) {
        LambdaQueryWrapper<FormField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FormField::getFormId, formId);
        queryWrapper.orderByAsc(FormField::getSort);
        return list(queryWrapper);
    }

    @Override
    public List<FormField> selectByFormIdAndType(Long formId, Integer type) {
        return baseMapper.selectByFormIdAndType(formId, type);
    }

    @Override
    public List<FormField> selectByFormIdAndGroupName(Long formId, String groupName) {
        return baseMapper.selectByFormIdAndGroupName(formId, groupName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(FormField formField) {
        return save(formField);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(FormField formField) {
        return updateById(formField);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCreate(Long formId, List<FormField> formFields) {
        formFields.forEach(field -> field.setFormId(formId));
        return saveBatch(formFields);
    }
}