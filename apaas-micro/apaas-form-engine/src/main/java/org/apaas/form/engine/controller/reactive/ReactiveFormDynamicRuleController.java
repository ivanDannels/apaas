package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.domain.Page;
import org.apaas.form.engine.entity.FormDynamicRule;
import org.apaas.form.engine.service.reactive.ReactiveFormDynamicRuleService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式表单动态规则控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-dynamic-rules")
@Tag(name = "响应式表单动态规则管理", description = "响应式表单动态规则相关操作")
@RequiredArgsConstructor
public class ReactiveFormDynamicRuleController {

    private final ReactiveFormDynamicRuleService dynamicRuleService;

    /**
     * 分页查询表单动态规则
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询表单动态规则", description = "根据表单ID分页查询表单动态规则列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public Mono<Page<FormDynamicRule>> selectPage(
            @RequestParam Long formId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        return dynamicRuleService.selectPage(formId, pageNum, pageSize);
    }

    /**
     * 根据表单ID查询动态规则列表
     */
    @GetMapping(value = "/form/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID查询动态规则列表", description = "根据表单ID查询所有动态规则列表")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormDynamicRule> selectByFormId(@PathVariable Long formId) {
        return dynamicRuleService.selectByFormId(formId);
    }

    /**
     * 根据表单ID和规则类型查询动态规则列表
     */
    @GetMapping(value = "/form/{formId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和规则类型查询动态规则列表", description = "根据表单ID和规则类型查询动态规则列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "type", description = "规则类型", required = true)
    })
    public Flux<FormDynamicRule> selectByFormIdAndType(
            @PathVariable Long formId,
            @PathVariable Integer type) {
        return dynamicRuleService.selectByFormIdAndType(formId, type);
    }

    /**
     * 根据目标字段ID查询动态规则列表
     */
    @GetMapping(value = "/field/{targetFieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据目标字段ID查询动态规则列表", description = "根据目标字段ID查询动态规则列表")
    @Parameter(name = "targetFieldId", description = "目标字段ID", required = true)
    public Flux<FormDynamicRule> selectByTargetFieldId(@PathVariable Long targetFieldId) {
        return dynamicRuleService.selectByTargetFieldId(targetFieldId);
    }

    /**
     * 根据目标字段ID和规则类型查询动态规则列表
     */
    @GetMapping(value = "/field/{targetFieldId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据目标字段ID和规则类型查询动态规则列表", description = "根据目标字段ID和规则类型查询动态规则列表")
    @Parameters({
        @Parameter(name = "targetFieldId", description = "目标字段ID", required = true),
        @Parameter(name = "type", description = "规则类型", required = true)
    })
    public Flux<FormDynamicRule> selectByTargetFieldIdAndType(
            @PathVariable Long targetFieldId,
            @PathVariable Integer type) {
        return dynamicRuleService.selectByTargetFieldIdAndType(targetFieldId, type);
    }

    /**
     * 获取表单动态规则详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单动态规则详情", description = "根据ID获取表单动态规则详情")
    @Parameter(name = "id", description = "规则ID", required = true)
    public Mono<FormDynamicRule> getById(@PathVariable Long id) {
        return dynamicRuleService.findById(id);
    }

    /**
     * 创建表单动态规则
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单动态规则", description = "创建新的表单动态规则")
    public Mono<FormDynamicRule> create(@RequestBody FormDynamicRule dynamicRule) {
        return dynamicRuleService.create(dynamicRule);
    }

    /**
     * 更新表单动态规则
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单动态规则", description = "更新表单动态规则信息")
    @Parameter(name = "id", description = "规则ID", required = true)
    public Mono<FormDynamicRule> update(@PathVariable Long id, @RequestBody FormDynamicRule dynamicRule) {
        dynamicRule.setId(id);
        return dynamicRuleService.update(dynamicRule);
    }

    /**
     * 删除表单动态规则
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单动态规则", description = "删除表单动态规则")
    @Parameter(name = "id", description = "规则ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return dynamicRuleService.delete(id);
    }

    /**
     * 批量创建表单动态规则
     */
    @PostMapping(value = "/batch/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量创建表单动态规则", description = "批量创建表单动态规则")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormDynamicRule> batchCreate(@PathVariable Long formId, @RequestBody List<FormDynamicRule> dynamicRules) {
        return dynamicRuleService.batchCreate(formId, Flux.fromIterable(dynamicRules));
    }
}