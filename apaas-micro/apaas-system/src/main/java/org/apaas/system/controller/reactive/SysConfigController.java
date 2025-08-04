package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.web.controller.ReactiveBaseController;
import org.apaas.system.entity.SysConfig;
import org.apaas.system.service.reactive.ReactiveSysConfigService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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
     * 修改参数配置状态
     */
    @PutMapping(value = "/status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "修改参数配置状态", description = "修改参数配置状态")
    @Parameters({
            @Parameter(name = "id", description = "参数配置ID", required = true),
            @Parameter(name = "status", description = "状态：0-正常，1-停用", required = true)
    })
    public Mono<Boolean> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        return service.changeStatus(id, status);
    }

    /**
     * 根据参数编码查询参数配置
     */
    @GetMapping(value = "/code/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据参数编码查询参数配置", description = "根据参数编码查询参数配置")
    @Parameter(name = "code", description = "参数编码", required = true)
    public Mono<SysConfig> getConfigByCode(@PathVariable String code) {
        return service.getConfigByCode(code);
    }

}