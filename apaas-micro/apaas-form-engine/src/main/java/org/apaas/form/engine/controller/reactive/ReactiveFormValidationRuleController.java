package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.core.query.PageResult;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.form.engine.entity.FormValidationRule;
import org.apaas.form.engine.service.reactive.ReactiveFormValidationRuleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式表单验证规则控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-validation-rules")
@Tag(name = "响应式表单验证规则管理", description = "响应式表单验证规则相关操作")
public class ReactiveFormValidationRuleController extends ReactiveBaseController<FormValidationRule, Long, ReactiveFormValidationRuleService> {

    public ReactiveFormValidationRuleController(ReactiveFormValidationRuleService service) {
        super(service);
    }

    /**
     * 分页查询表单验证规则
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询表单验证规则", description = "根据字段ID分页查询表单验证规则列表")
    @Parameters({
        @Parameter(name = "fieldId", description = "字段ID", required = true),
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public Mono<PageResult<FormValidationRule>> selectPage(
            @RequestParam Long fieldId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        return service.selectPage(fieldId, pageNum, pageSize);
    }

    /**
     * 根据字段ID查询验证规则列表
     */
    @GetMapping(value = "/field/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字段ID查询验证规则列表", description = "根据字段ID查询所有验证规则列表")
    @Parameter(name = "fieldId", description = "字段ID", required = true)
    public Flux<FormValidationRule> selectByFieldId(@PathVariable Long fieldId) {
        return service.selectByFieldId(fieldId);
    }

    /**
     * 根据字段ID和规则类型查询验证规则列表
     */
    @GetMapping(value = "/field/{fieldId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字段ID和规则类型查询验证规则列表", description = "根据字段ID和规则类型查询验证规则列表")
    @Parameters({
        @Parameter(name = "fieldId", description = "字段ID", required = true),
        @Parameter(name = "type", description = "规则类型", required = true)
    })
    public Flux<FormValidationRule> selectByFieldIdAndType(
            @PathVariable Long fieldId,
            @PathVariable Integer type) {
        return service.selectByFieldIdAndType(fieldId, type);
    }

    /**
     * 根据字段ID列表查询验证规则列表
     */
    @PostMapping(value = "/fields", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字段ID列表查询验证规则列表", description = "根据字段ID列表查询验证规则列表")
    public Flux<FormValidationRule> selectByFieldIds(@RequestBody List<Long> fieldIds) {
        return service.selectByFieldIds(fieldIds);
    }

    /**
     * 获取表单验证规则详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单验证规则详情", description = "根据ID获取表单验证规则详情")
    @Parameter(name = "id", description = "规则ID", required = true)
    public Mono<FormValidationRule> getById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * 创建表单验证规则
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单验证规则", description = "创建新的表单验证规则")
    public Mono<FormValidationRule> create(@RequestBody FormValidationRule validationRule) {
        return service.create(validationRule);
    }

    /**
     * 更新表单验证规则
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单验证规则", description = "更新表单验证规则信息")
    @Parameter(name = "id", description = "规则ID", required = true)
    public Mono<FormValidationRule> update(@PathVariable Long id, @RequestBody FormValidationRule validationRule) {
        validationRule.setId(id);
        return service.update(validationRule);
    }

    /**
     * 删除表单验证规则
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单验证规则", description = "删除表单验证规则")
    @Parameter(name = "id", description = "规则ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    /**
     * 批量创建表单验证规则
     */
    @PostMapping(value = "/batch/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量创建表单验证规则", description = "批量创建表单验证规则")
    @Parameter(name = "fieldId", description = "字段ID", required = true)
    public Flux<FormValidationRule> batchCreate(@PathVariable Long fieldId, @RequestBody List<FormValidationRule> validationRules) {
        return service.batchCreate(fieldId, Flux.fromIterable(validationRules));
    }
}