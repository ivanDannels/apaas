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
import org.apaas.system.domain.entity.SysDictData;
import org.apaas.system.service.ISysDictDataService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 数据字典信息
 */
@Tag(name = "字典数据管理")
@RestController
@RequestMapping("/system/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final ISysDictDataService dictDataService;

    @Operation(summary = "分页查询字典数据列表")
    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PageResult<SysDictData>> list(BasePageQuery query) {
        Page<SysDictData> page = new Page<>(query.getPageNum(), query.getPageSize());
        Page<SysDictData> result = dictDataService.selectDictDataPage(page, query);
        return Mono.just(PageResult.build(result));
    }

    @Operation(summary = "导出字典数据列表")
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(BasePageQuery query) {
        // TODO: 实现导出功能
    }

    @Operation(summary = "查询字典数据详细")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> getInfo(@Parameter(description = "字典数据ID") @PathVariable Long dictCode) {
        return Mono.just(AjaxResult.success(dictDataService.getById(dictCode)));
    }

    @Operation(summary = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/type/{dictType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> dictType(@Parameter(description = "字典类型") @PathVariable String dictType) {
        List<SysDictData> data = dictDataService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = List.of();
        }
        return Mono.just(AjaxResult.success(data));
    }

    @Operation(summary = "新增字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> add(@Validated @RequestBody SysDictData dict) {
        return Mono.just(AjaxResult.success(dictDataService.insertDictData(dict)));
    }

    @Operation(summary = "修改字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> edit(@Validated @RequestBody SysDictData dict) {
        return Mono.just(AjaxResult.success(dictDataService.updateDictData(dict)));
    }

    @Operation(summary = "删除字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{dictCodes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AjaxResult> remove(@Parameter(description = "字典数据ID数组") @PathVariable Long[] dictCodes) {
        return Mono.just(AjaxResult.success(dictDataService.deleteDictDataByIds(dictCodes)));
    }
}