package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.system.domain.dto.SysConfigDTO;
import org.apaas.system.entity.SysConfig;
import org.apaas.system.service.reactive.ReactiveSysConfigService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * 参数配置控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/configs")
@Tag(name = "参数配置管理", description = "参数配置管理API")
@RequiredArgsConstructor
public class SysConfigController {

    private final ReactiveSysConfigService configService;

    /**
     * 分页查询参数配置列表
     */
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询参数配置列表", description = "分页查询参数配置列表")
    public Mono<PageResult<SysConfig>> getConfigPage(SysConfigDTO query) {
        return configService.getConfigPage(query.buildPage(), query);
    }

    /**
     * 新增参数配置
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "新增参数配置", description = "新增参数配置")
    public Mono<Boolean> addConfig(@RequestBody SysConfig config) {
        return configService.addConfig(config);
    }

    /**
     * 修改参数配置
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改参数配置", description = "修改参数配置")
    public Mono<Boolean> updateConfig(@RequestBody SysConfig config) {
        return configService.updateConfig(config);
    }

    /**
     * 删除参数配置
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除参数配置", description = "删除参数配置")
    @Parameter(name = "id", description = "参数配置ID", required = true)
    public Mono<Boolean> deleteConfig(@PathVariable Long id) {
        return configService.deleteConfig(id);
    }

    /**
     * 批量删除参数配置
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除参数配置", description = "批量删除参数配置")
    @Parameter(name = "ids", description = "参数配置ID数组", required = true)
    public Mono<Boolean> batchDeleteConfig(@RequestBody Long[] ids) {
        return configService.batchDeleteConfig(ids);
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
        return configService.changeStatus(id, status);
    }

    /**
     * 根据参数编码查询参数配置
     */
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据参数编码查询参数配置", description = "根据参数编码查询参数配置")
    @Parameter(name = "code", description = "参数编码", required = true)
    public Mono<SysConfig> getConfigByCode(@PathVariable String code) {
        return configService.getConfigByCode(code);
    }

    /**
     * 导出参数配置
     */
    @GetMapping("/export")
    @Operation(summary = "导出参数配置", description = "导出参数配置")
    public Mono<Void> exportExcel(ServerWebExchange exchange, SysConfigDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return Mono.fromCallable(() -> {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                configService.exportExcel(outputStream, query);
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException("Failed to export excel file", e);
            }
        })
        .subscribeOn(Schedulers.boundedElastic())
        .flatMap(bytes -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap(bytes);
            return response.writeWith(Mono.just(dataBuffer));
        })
        .onErrorResume(e -> {
            response.setStatusCode(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
            return response.setComplete();
        });
    }

    /**
     * 导入参数配置
     */
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入参数配置", description = "导入参数配置")
    @Parameter(name = "file", description = "Excel文件", required = true)
    public Mono<Boolean> importExcel(@RequestPart("file") FilePart file) {
        return file.content()
            .publishOn(Schedulers.boundedElastic())
            .reduce(new ByteArrayOutputStream(), (outputStream, dataBuffer) -> {
                try {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    outputStream.write(bytes);
                    return outputStream;
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read file content", e);
                } finally {
                    dataBuffer.release();
                }
            })
            .flatMap(outputStream -> Mono.fromCallable(() -> {
                try (InputStream inputStream = new java.io.ByteArrayInputStream(outputStream.toByteArray())) {
                    return configService.importExcel(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to import excel file", e);
                }
            }))
            .subscribeOn(Schedulers.boundedElastic());
    }
}