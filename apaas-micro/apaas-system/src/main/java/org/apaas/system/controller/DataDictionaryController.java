package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionary;
import org.apaas.system.service.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import com.baomidou.mybatisplus.core.metadata.IPage;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 数据字典控制器
 */
@RestController
@RequestMapping("/api/v1/data-dictionaries")
@Tag(name = "数据字典管理", description = "数据字典相关操作")
public class DataDictionaryController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    /**
     * 分页查询数据字典
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询数据字典", description = "根据条件分页查询数据字典列表")
    @Parameters({
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true),
        @Parameter(name = "name", description = "字典名称，模糊查询"),
        @Parameter(name = "type", description = "字典类型：0-系统字典，1-业务字典"),
        @Parameter(name = "status", description = "状态：0-正常，1-停用")
    })
    public Mono<IPage<DataDictionary>> selectPage(DataDictionaryDTO query) {
        IPage<DataDictionary> page = dataDictionaryService.selectPage(query);
        return Mono.just(page);
    }

    /**
     * 获取数据字典详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取数据字典详情", description = "根据ID获取数据字典详情")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<DataDictionary> getById(@PathVariable Long id) {
        DataDictionary dataDictionary = dataDictionaryService.getById(id);
        return Mono.just(dataDictionary);
    }

    /**
     * 创建数据字典
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建数据字典", description = "创建新的数据字典")
    public Mono<Boolean> create(@RequestBody DataDictionary dataDictionary) {
        boolean result = dataDictionaryService.create(dataDictionary);
        return Mono.just(result);
    }

    /**
     * 更新数据字典
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新数据字典", description = "更新数据字典信息")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody DataDictionary dataDictionary) {
        dataDictionary.setId(id);
        boolean result = dataDictionaryService.update(dataDictionary);
        return Mono.just(result);
    }

    /**
     * 删除数据字典
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除数据字典", description = "删除数据字典")
    @Parameter(name = "id", description = "数据字典ID", required = true)
    public Mono<Boolean> delete(@PathVariable Long id) {
        boolean result = dataDictionaryService.delete(id);
        return Mono.just(result);
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出数据字典", description = "导出数据字典")
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) throws IOException {
        return dataDictionaryService.exportExcel(exchange.getResponse(), query);
    }

    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入数据字典", description = "导入数据字典")
    @Parameter(name = "file", description = "Excel文件", required = true)
    public Mono<Boolean> importExcel(@RequestPart("file") FilePart file) throws IOException {
        boolean result = dataDictionaryService.importExcel(file);
        return Mono.just(result);
    }

    /**
     * 启用/停用数据字典
     */
    @PutMapping(value = "/{id}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "启用/停用数据字典", description = "启用或停用数据字典")
    @Parameters({
        @Parameter(name = "id", description = "数据字典ID", required = true),
        @Parameter(name = "status", description = "状态：0-启用，1-停用", required = true)
    })
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        boolean result = dataDictionaryService.changeStatus(id, status);
        return Mono.just(result);
    }
}