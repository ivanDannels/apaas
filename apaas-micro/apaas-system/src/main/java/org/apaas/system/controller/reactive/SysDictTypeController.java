package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.system.domain.dto.SysDictTypeDTO;
import org.apaas.system.domain.entity.SysDictType;
import org.apaas.system.service.reactive.ReactiveSysDictTypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 数据字典信息
 */
@Tag(name = "响应式字典类型管理", description = "响应式字典类型相关操作")
@RestController
@RequestMapping("/api/v1/reactive/dict-types")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final ReactiveSysDictTypeService dictTypeService;

    @Operation(summary = "分页查询字典类型列表")
    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "dictName", description = "字典名称，模糊查询"),
        @Parameter(name = "dictType", description = "字典类型，模糊查询"),
        @Parameter(name = "status", description = "状态：0-正常，1-停用")
    })
    public Flux<SysDictType> list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String dictName,
            @RequestParam(required = false) String dictType,
            @RequestParam(required = false) Integer status) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        SysDictTypeDTO query = new SysDictTypeDTO();
        query.setDictName(dictName);
        query.setDictType(dictType);
        query.setStatus(status);
        return dictTypeService.selectPage(pageable, query);
    }

    @Operation(summary = "导出字典类型列表")
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Void> exportExcel(ServerWebExchange exchange, SysDictTypeDTO query) {
        return dictTypeService.exportExcel(exchange, query);
    }

    @Operation(summary = "查询字典类型详细")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SysDictType> getInfo(@Parameter(description = "字典ID") @PathVariable Long dictId) {
        return dictTypeService.getById(dictId)
            .switchIfEmpty(Mono.error(new RuntimeException("字典类型不存在")));
    }

    @Operation(summary = "新增字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> add(@Validated @RequestBody SysDictType dict) {
        return dictTypeService.checkDictTypeUnique(dict)
            .flatMap(unique -> {
                if (!unique) {
                    return Mono.error(new RuntimeException("新增字典'" + dict.getDictName() + "'失败，字典类型已存在"));
                }
                return dictTypeService.insertDictType(dict);
            });
    }

    @Operation(summary = "修改字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{dictId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> edit(
            @Parameter(description = "字典ID") @PathVariable Long dictId,
            @Validated @RequestBody SysDictType dict) {
        dict.setDictId(dictId);
        return dictTypeService.checkDictTypeUnique(dict)
            .flatMap(unique -> {
                if (!unique) {
                    return Mono.error(new RuntimeException("修改字典'" + dict.getDictName() + "'失败，字典类型已存在"));
                }
                return dictTypeService.updateDictType(dict);
            });
    }

    @Operation(summary = "删除字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{dictId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> remove(@Parameter(description = "字典ID") @PathVariable Long dictId) {
        return dictTypeService.deleteDictTypeById(dictId);
    }

    @Operation(summary = "批量删除字典类型")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> batchRemove(@RequestBody Long[] dictIds) {
        return dictTypeService.deleteDictTypeByIds(dictIds);
    }

    @Operation(summary = "刷新字典缓存")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping(value = "/refresh-cache", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> refreshCache() {
        return dictTypeService.resetDictCache();
    }

    @Operation(summary = "获取字典选择框列表")
    @GetMapping(value = "/options", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SysDictType> getOptions() {
        return dictTypeService.list();
    }
}