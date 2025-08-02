package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.system.domain.dto.DataDictionaryItemDTO;
import org.apaas.system.entity.DataDictionaryItem;
import org.apaas.system.service.reactive.ReactiveDataDictionaryItemService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * 响应式数据字典项控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/data-dictionary-items")
@Tag(name = "响应式数据字典项管理", description = "响应式数据字典项相关操作")
@RequiredArgsConstructor
public class DataDictionaryItemController {

    private final ReactiveDataDictionaryItemService dataDictionaryItemService;

    /**
     * 分页查询数据字典项
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询数据字典项", description = "根据条件分页查询数据字典项列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "dictionaryId", description = "字典ID"),
        @Parameter(name = "name", description = "字典项名称，模糊查询"),
        @Parameter(name = "code", description = "字典项编码，模糊查询"),
        @Parameter(name = "status", description = "状态：0-正常，1-停用")
    })
    public Flux<DataDictionaryItem> selectPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Long dictionaryId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Integer status) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNum - 1);
        DataDictionaryItemDTO query = new DataDictionaryItemDTO();
        query.setDictionaryId(dictionaryId);
        query.setName(name);
        query.setCode(code);
        query.setStatus(status);
        return dataDictionaryItemService.selectPage(pageable, query);
    }

    /**
     * 获取数据字典项详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取数据字典项详情", description = "根据ID获取数据字典项详情")
    @Parameter(name = "id", description = "数据字典项ID", required = true)
    public Mono<DataDictionaryItem> getById(@PathVariable Long id) {
        return dataDictionaryItemService.getById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("数据字典项不存在")));
    }

    /**
     * 根据字典ID查询字典项列表
     */
    @GetMapping(value = "/by-dictionary/{dictionaryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字典ID查询字典项列表", description = "根据字典ID查询有效的字典项列表")
    @Parameter(name = "dictionaryId", description = "字典ID", required = true)
    public Flux<DataDictionaryItem> getByDictionaryId(@PathVariable Long dictionaryId) {
        return dataDictionaryItemService.selectByDictionaryId(dictionaryId);
    }

    /**
     * 创建数据字典项
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建数据字典项", description = "创建新的数据字典项")
    public Mono<Boolean> create(@RequestBody DataDictionaryItem dataDictionaryItem) {
        return dataDictionaryItemService.create(dataDictionaryItem);
    }

    /**
     * 更新数据字典项
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新数据字典项", description = "更新数据字典项信息")
    @Parameter(name = "id", description = "数据字典项ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody DataDictionaryItem dataDictionaryItem) {
        dataDictionaryItem.setId(id);
        return dataDictionaryItemService.update(dataDictionaryItem);
    }

    /**
     * 删除数据字典项
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除数据字典项", description = "删除数据字典项")
    @Parameter(name = "id", description = "数据字典项ID", required = true)
    public Mono<Boolean> delete(@PathVariable Long id) {
        return dataDictionaryItemService.delete(id);
    }

    /**
     * 导出数据字典项
     */
    @GetMapping(value = "/export/{dictionaryId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出数据字典项", description = "导出数据字典项")
    @Parameter(name = "dictionaryId", description = "数据字典ID", required = true)
    public Mono<Void> exportExcel(ServerWebExchange exchange, @PathVariable Long dictionaryId, DataDictionaryItemDTO query) {
        return dataDictionaryItemService.exportExcel(exchange.getResponse(), dictionaryId, query);
    }

    /**
     * 导入数据字典项
     */
    @PostMapping(value = "/import/{dictionaryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入数据字典项", description = "导入数据字典项")
    @Parameters({
        @Parameter(name = "dictionaryId", description = "数据字典ID", required = true),
        @Parameter(name = "file", description = "Excel文件", required = true)
    })
    public Mono<Boolean> importExcel(@PathVariable Long dictionaryId, @RequestPart("file") Mono<FilePart> filePart) {
        return dataDictionaryItemService.importExcel(dictionaryId, filePart);
    }

    /**
     * 启用/停用数据字典项
     */
    @PutMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启用/停用数据字典项", description = "启用或停用数据字典项")
    @Parameters({
        @Parameter(name = "id", description = "数据字典项ID", required = true),
        @Parameter(name = "status", description = "状态：0-启用，1-停用", required = true)
    })
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return dataDictionaryItemService.changeStatus(id, status);
    }
}