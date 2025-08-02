package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.annotation.Log;
import org.apaas.core.enums.BusinessType;
import org.apaas.system.domain.dto.SysDictDataDTO;
import org.apaas.system.domain.entity.SysDictData;
import org.apaas.system.service.reactive.ReactiveSysDictDataService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 数据字典信息
 */
@Tag(name = "响应式字典数据管理", description = "响应式字典数据相关操作")
@RestController
@RequestMapping("/api/v1/reactive/dict-data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final ReactiveSysDictDataService dictDataService;

    @Operation(summary = "分页查询字典数据列表")
    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "dictType", description = "字典类型"),
        @Parameter(name = "status", description = "状态：0-正常，1-停用")
    })
    public Flux<SysDictData> list(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String dictType,
            @RequestParam(required = false) Integer status) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        SysDictDataDTO query = new SysDictDataDTO();
        query.setDictType(dictType);
        query.setStatus(status);
        return dictDataService.selectPage(pageable, query);
    }

    @Operation(summary = "导出字典数据列表")
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<Void> exportExcel(ServerWebExchange exchange, SysDictDataDTO query) {
        return dictDataService.exportExcel(exchange, query);
    }

    @Operation(summary = "查询字典数据详细")
    @PreAuthorize("@ss.hasPermi('system:dict:query')")
    @GetMapping(value = "/{dictCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<SysDictData> getInfo(@Parameter(description = "字典数据ID") @PathVariable Long dictCode) {
        return dictDataService.getById(dictCode)
            .switchIfEmpty(Mono.error(new RuntimeException("字典数据不存在")));
    }

    @Operation(summary = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/type/{dictType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<SysDictData> dictType(@Parameter(description = "字典类型") @PathVariable String dictType) {
        return dictDataService.selectDictDataByType(dictType);
    }

    @Operation(summary = "新增字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> add(@Validated @RequestBody SysDictData dict) {
        return dictDataService.insertDictData(dict);
    }

    @Operation(summary = "修改字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{dictCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> edit(
            @Parameter(description = "字典数据ID") @PathVariable Long dictCode,
            @Validated @RequestBody SysDictData dict) {
        dict.setDictCode(dictCode);
        return dictDataService.updateDictData(dict);
    }

    @Operation(summary = "删除字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{dictCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> remove(@Parameter(description = "字典数据ID") @PathVariable Long dictCode) {
        return dictDataService.deleteDictDataById(dictCode);
    }

    @Operation(summary = "批量删除字典数据")
    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> batchRemove(@RequestBody Long[] dictCodes) {
        return dictDataService.deleteDictDataByIds(dictCodes);
    }
}