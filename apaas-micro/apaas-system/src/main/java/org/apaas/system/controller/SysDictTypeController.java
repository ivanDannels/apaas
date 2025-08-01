package org.apaas.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.core.utils.StringUtils;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.web.domain.PageResult;
import org.apaas.system.domain.entity.SysDictType;
import org.apaas.system.service.ISysDictTypeService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 数据字典信息
 */
@Tag(name = "字典类型管理")
@RestController
@RequestMapping("/system/dict/type")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final ISysDictTypeService dictTypeService;

    @Operation(summary = "分页查询字典类型列表")
    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PageResult<SysDictType>> list(BasePageQuery query) {
        Page<SysDictType> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<SysDictType> result = dictTypeService.selectDictTypePage(page, query);
        return Mono.just(PageResult.build(result));
    }

    @Operation(summary = "导出字典类型列表")
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(BasePageQuery query) {
        // TODO: 实现导出功能
    }

    @Operation(summary = "查询字典类型详细")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> getInfo(@Parameter(description = "字典ID") @PathVariable Long dictId) {
        return Mono.just(AjaxResult.success(dictTypeService.getById(dictId)));
    }

    @Operation(summary = "新增字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> add(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return Mono.just(AjaxResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在"));
        }
        return Mono.just(AjaxResult.success(dictTypeService.insertDictType(dict)));
    }

    @Operation(summary = "修改字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> edit(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return Mono.just(AjaxResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在"));
        }
        return Mono.just(AjaxResult.success(dictTypeService.updateDictType(dict)));
    }

    @Operation(summary = "删除字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{dictIds}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> remove(@Parameter(description = "字典ID数组") @PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "刷新字典缓存")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping(value = "/refreshCache", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> refreshCache() {
        dictTypeService.resetDictCache();
        return Mono.just(AjaxResult.success());
    }

    @Operation(summary = "获取字典选择框列表")
    @GetMapping(value = "/optionselect", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> optionselect() {
        List<SysDictType> dictTypes = dictTypeService.list();
        return Mono.just(AjaxResult.success(dictTypes));
    }
}