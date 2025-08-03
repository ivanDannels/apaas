package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.query.PageResult;
import org.apaas.form.engine.domain.dto.FormDefinitionDTO;
import org.apaas.form.engine.entity.FormDefinition;
import org.apaas.form.engine.service.reactive.ReactiveFormDefinitionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单定义控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-definitions")
@Tag(name = "响应式表单定义管理", description = "响应式表单定义相关操作")
@RequiredArgsConstructor
public class ReactiveFormDefinitionController {

    private final ReactiveFormDefinitionService formDefinitionService;

    /**
     * 获取表单定义列表
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单定义列表", description = "分页查询表单定义信息")
    public Mono<PageResult<FormDefinition>> list(FormDefinitionDTO query) {
        return formDefinitionService.selectFormDefinitionPage(query);
    }

    /**
     * 获取表单定义详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单定义详情", description = "根据ID查询表单定义信息")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<FormDefinition> getById(@PathVariable Long id) {
        return formDefinitionService.findById(id);
    }

    /**
     * 创建表单定义
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单定义", description = "新增表单定义信息")
    public Mono<Long> save(@RequestBody FormDefinition formDefinition) {
        return formDefinitionService.saveFormDefinition(formDefinition);
    }

    /**
     * 更新表单定义
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单定义", description = "修改表单定义信息")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody FormDefinition formDefinition) {
        formDefinition.setId(id);
        return formDefinitionService.updateFormDefinition(formDefinition);
    }

    /**
     * 删除表单定义
     */
    @DeleteMapping(value = "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单定义", description = "批量删除表单定义")
    @Parameter(name = "ids", description = "表单ID集合", required = true)
    public Mono<Boolean> remove(@PathVariable Long[] ids) {
        return formDefinitionService.deleteFormDefinitions(ids);
    }

    /**
     * 发布表单定义
     */
    @PostMapping(value = "/publish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发布表单定义", description = "发布表单定义为可用状态")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Boolean> publish(@PathVariable Long id) {
        return formDefinitionService.publishFormDefinition(id);
    }

    /**
     * 停用表单定义
     */
    @PostMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "停用表单定义", description = "将表单定义设置为停用状态")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Boolean> disable(@PathVariable Long id) {
        return formDefinitionService.disableFormDefinition(id);
    }

    /**
     * 获取表单版本列表
     */
    @GetMapping(value = "/versions/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单版本列表", description = "根据表单编码查询所有版本")
    @Parameter(name = "code", description = "表单编码", required = true)
    public Flux<FormDefinition> getVersionsByCode(@PathVariable String code) {
        return formDefinitionService.getVersionsByCode(code);
    }

    /**
     * 复制表单定义
     */
    @PostMapping(value = "/copy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "复制表单定义", description = "复制现有表单定义创建新表单")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Long> copy(@PathVariable Long id, @RequestParam String newName) {
        return formDefinitionService.copyFormDefinition(id, newName);
    }

    /**
     * 导出表单定义
     */
    @GetMapping(value = "/export/{id}")
    @Operation(summary = "导出表单定义", description = "导出表单定义为JSON文件")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<ResponseEntity<byte[]>> export(@PathVariable Long id) {
        return formDefinitionService.exportFormDefinition(id)
                .map(data -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=form-" + id + ".json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data));
    }

    /**
     * 导入表单定义
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入表单定义", description = "从JSON数据导入表单定义")
    public Mono<Long> importForm(@RequestBody byte[] data) {
        return formDefinitionService.importFormDefinition(data);
    }
}