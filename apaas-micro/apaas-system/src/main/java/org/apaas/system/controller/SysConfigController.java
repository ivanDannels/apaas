package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.system.domain.dto.SysConfigDTO;
import org.apaas.system.entity.SysConfig;
import org.apaas.system.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * 参数配置控制器
 */
@RestController
@RequestMapping("/config")
@Tag(name = "参数配置管理", description = "参数配置管理API")
public class SysConfigController {

    @Autowired
    private SysConfigService configService;

    /**
     * 分页查询参数配置列表
     */
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询参数配置列表", description = "分页查询参数配置列表")
    public Mono<Object> getConfigPage(SysConfigDTO query) {
        return Mono.just(configService.getConfigPage(query.buildPage(), query));
    }

    /**
     * 新增参数配置
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增参数配置", description = "新增参数配置")
    public Mono<Boolean> addConfig(@RequestBody SysConfig config) {
        return Mono.just(configService.addConfig(config));
    }

    /**
     * 修改参数配置
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改参数配置", description = "修改参数配置")
    public Mono<Boolean> updateConfig(@RequestBody SysConfig config) {
        return Mono.just(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除参数配置", description = "删除参数配置")
    @Parameter(name = "id", description = "参数配置ID", required = true)
    public Mono<Boolean> deleteConfig(@PathVariable Long id) {
        return Mono.just(configService.deleteConfig(id));
    }

    /**
     * 批量删除参数配置
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除参数配置", description = "批量删除参数配置")
    @Parameter(name = "ids", description = "参数配置ID数组", required = true)
    public Mono<Boolean> batchDeleteConfig(@RequestBody Long[] ids) {
        return Mono.just(configService.batchDeleteConfig(ids));
    }

    /**
     * 修改参数配置状态
     */
    @PutMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改参数配置状态", description = "修改参数配置状态")
    @Parameters({
        @Parameter(name = "id", description = "参数配置ID", required = true),
        @Parameter(name = "status", description = "状态：0-正常，1-停用", required = true)
    })
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return Mono.just(configService.changeStatus(id, status));
    }

    /**
     * 根据参数编码查询参数配置
     */
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据参数编码查询参数配置", description = "根据参数编码查询参数配置")
    @Parameter(name = "code", description = "参数编码", required = true)
    public Mono<SysConfig> getConfigByCode(@PathVariable String code) {
        return Mono.just(configService.getConfigByCode(code));
    }

    /**
     * 导出参数配置
     */
    @GetMapping("/export")
    @Operation(summary = "导出参数配置", description = "导出参数配置")
    public Mono<Void> exportExcel(ServerWebExchange exchange, SysConfigDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        try {
            // 这里需要修改service层的exportExcel方法以支持响应式
            // 暂时使用阻塞方式处理
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            configService.exportExcel(outputStream, query);
            byte[] bytes = outputStream.toByteArray();
            DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap(bytes);
            response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return response.writeWith(Mono.just(dataBuffer));
        } catch (IOException e) {
            response.setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
        }
    }

    /**
     * 导入参数配置
     */
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入参数配置", description = "导入参数配置")
    @Parameter(name = "file", description = "Excel文件", required = true)
    public Mono<Boolean> importExcel(@RequestPart("file") FilePart file) {
        // 这里需要修改service层的importExcel方法以支持响应式
        // 暂时使用阻塞方式处理
        return Mono.fromCallable(() -> {
            try {
                java.io.InputStream inputStream = file.content().blockFirst().asInputStream();
                return configService.importExcel(inputStream);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}