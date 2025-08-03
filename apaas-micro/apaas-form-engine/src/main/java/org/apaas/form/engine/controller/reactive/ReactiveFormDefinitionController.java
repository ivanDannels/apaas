package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.query.PageResult;
import org.apaas.core.query.Query;
import org.apaas.core.web.controller.ReactiveBaseController;
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
public class ReactiveFormDefinitionController extends ReactiveBaseController<FormDefinition, Long, ReactiveFormDefinitionService> {

    /**
     * 获取表单定义列表
     */
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单定义列表", description = "分页查询表单定义信息")
    public Mono<PageResult<FormDefinition>> list(@RequestBody Query query) {
        return service.selectPage(query);
    }

    /**
     * 获取表单定义详情
     */
    @Override
    @Operation(summary = "获取表单定义详情", description = "根据ID查询表单定义信息")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<FormDefinition> get(@PathVariable Long id) {
        return super.get(id);
    }

    /**
     * 创建表单定义
     */
    @Override
    @Operation(summary = "创建表单定义", description = "新增表单定义信息")
    public Mono<FormDefinition> add(@RequestBody FormDefinition formDefinition) {
        return service.saveFormDefinition(formDefinition).then(super.add(formDefinition));
    }

    /**
     * 更新表单定义
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单定义", description = "修改表单定义信息")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<FormDefinition> update(@PathVariable Long id, @RequestBody FormDefinition formDefinition) {
        formDefinition.setId(id);
        return service.updateFormDefinition(formDefinition).then(super.update(formDefinition));
    }

    /**
     * 删除表单定义
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单定义", description = "删除表单定义")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteFormDefinitions(new Long[]{id}).then(super.delete(id));
    }
    
    /**
     * 批量删除表单定义
     */
    @DeleteMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量删除表单定义", description = "批量删除表单定义")
    public Mono<Void> deleteBatch(@RequestBody Long[] ids) {
        return service.deleteByIds(Flux.fromArray(ids)).then(Mono.empty());
    }
    
    /**
     * 批量新增表单定义
     */
    @PostMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量新增表单定义", description = "批量新增表单定义")
    public Flux<FormDefinition> addBatch(@RequestBody Flux<FormDefinition> forms) {
        return service.saveBatch(forms);
    }
    
    /**
     * 批量更新表单定义
     */
    @PutMapping(value = "/batch", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量更新表单定义", description = "批量更新表单定义")
    public Flux<FormDefinition> updateBatch(@RequestBody Flux<FormDefinition> forms) {
        return service.updateBatch(forms);
    }
    
    /**
     * 导出表单定义
     */
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导出表单定义", description = "导出表单定义")
    public Mono<byte[]> export(@RequestBody Query query) {
        return service.export(query);
    }
    
    /**
     * 导入表单定义
     */
    @PostMapping(value = "/import", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "导入表单定义", description = "导入表单定义")
    public Mono<Void> importData(@RequestBody byte[] data) {
        return service.importData(data);
    }

    /**
     * 发布表单定义
     */
    @PostMapping(value = "/publish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发布表单定义", description = "发布表单定义为可用状态")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Void> publish(@PathVariable Long id) {
        return service.publishFormDefinition(id).then(Mono.empty());
    }

    /**
     * 停用表单定义
     */
    @PostMapping(value = "/disable/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "停用表单定义", description = "将表单定义设置为停用状态")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Void> disable(@PathVariable Long id) {
        return service.disableFormDefinition(id).then(Mono.empty());
    }

    /**
     * 获取表单版本列表
     */
    @GetMapping(value = "/versions/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单版本列表", description = "根据表单编码查询所有版本")
    @Parameter(name = "code", description = "表单编码", required = true)
    public Flux<FormDefinition> getVersionsByCode(@PathVariable String code) {
        return service.getVersionsByCode(code);
    }

    /**
     * 复制表单定义
     */
    @PostMapping(value = "/copy/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "复制表单定义", description = "复制现有表单定义创建新表单")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<Long> copy(@PathVariable Long id, @RequestParam String newName) {
        return service.copyFormDefinition(id, newName);
    }

    /**
     * 导出表单定义
     */
    @GetMapping(value = "/export/{id}")
    @Operation(summary = "导出表单定义", description = "导出表单定义为JSON文件")
    @Parameter(name = "id", description = "表单ID", required = true)
    public Mono<ResponseEntity<byte[]>> export(@PathVariable Long id) {
        return service.exportFormDefinition(id)
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
        return service.importFormDefinition(data);
    }
}