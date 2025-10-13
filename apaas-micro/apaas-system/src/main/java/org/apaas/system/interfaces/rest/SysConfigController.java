package org.apaas.system.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.domain.interfaces.rest.ReactiveBaseController;
import org.apaas.system.domain.model.SysConfig;
import org.apaas.system.application.service.ReactiveSysConfigService;
import org.apaas.system.application.dto.SysConfigDTO;
import org.apaas.system.application.assembler.SysConfigAssembler;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 参数配置控制器
 * @author ivan
 */
@RestController
@RequestMapping("/api/v1/reactive/configs")
@Tag(name = "参数配置管理", description = "参数配置管理API")
public class SysConfigController extends ReactiveBaseController<SysConfig, Long, ReactiveSysConfigService> {
    
    public SysConfigController(ReactiveSysConfigService service) {
        super(service);
    }
    
    /**
     * 分页查询参数配置列表
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询参数配置列表", description = "分页查询参数配置列表")
    public Flux<SysConfigDTO> getConfigPage(@PageableDefault(size = 10) Pageable pageable, @RequestBody(required = false) SysConfigDTO query) {
        return service.getConfigPage(pageable, query)
                .map(SysConfigAssembler.INSTANCE::convertEntityToDto);
    }
    
    /**
     * 导出参数配置
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "导出参数配置", description = "导出参数配置")
    public Mono<Void> exportExcel(ServerWebExchange exchange, @RequestBody(required = false) SysConfigDTO query) {
        return service.exportExcel(exchange, query);
    }
    
    /**
     * 导入参数配置
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入参数配置", description = "导入参数配置")
    public Mono<Boolean> importExcel(@RequestBody byte[] fileData) {
        return service.importExcel(fileData);
    }
    
    /**
     * 修改参数配置状态
     */
    @PutMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改参数配置状态", description = "修改参数配置状态")
    @Parameters({@Parameter(name = "id", description = "参数配置ID", required = true), @Parameter(name = "status", description = "状态：0-正常，1-停用", required = true)})
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return service.changeStatus(id, status);
    }
    
    /**
     * 根据参数编码查询参数配置
     */
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据参数编码查询参数配置", description = "根据参数编码查询参数配置")
    @Parameter(name = "code", description = "参数编码", required = true)
    public Mono<SysConfigDTO> getConfigByCode(@PathVariable String code) {
        return service.getConfigByCode(code)
                .map(SysConfigAssembler.INSTANCE::convertEntityToDto);
    }
    
}