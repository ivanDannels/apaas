package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.query.PageResult;
import org.apaas.form.engine.entity.FormLayout;
import org.apaas.form.engine.service.reactive.ReactiveFormLayoutService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单布局控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-layouts")
@Tag(name = "响应式表单布局管理", description = "响应式表单布局相关操作")
@RequiredArgsConstructor
public class ReactiveFormLayoutController {

    private final ReactiveFormLayoutService layoutService;

    /**
     * 分页查询表单布局
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询表单布局", description = "根据表单ID分页查询表单布局列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public Mono<PageResult<FormLayout>> selectPage(
            @RequestParam Long formId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        return layoutService.selectPage(formId, pageNum, pageSize);
    }

    /**
     * 根据表单ID查询布局列表
     */
    @GetMapping(value = "/form/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID查询布局列表", description = "根据表单ID查询所有布局列表")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormLayout> selectByFormId(@PathVariable Long formId) {
        return layoutService.selectByFormId(formId);
    }

    /**
     * 根据表单ID和布局类型查询布局列表
     */
    @GetMapping(value = "/form/{formId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和布局类型查询布局列表", description = "根据表单ID和布局类型查询布局列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "type", description = "布局类型", required = true)
    })
    public Flux<FormLayout> selectByFormIdAndType(
            @PathVariable Long formId,
            @PathVariable Integer type) {
        return layoutService.selectByFormIdAndType(formId, type);
    }

    /**
     * 根据表单ID和终端类型查询布局列表
     */
    @GetMapping(value = "/form/{formId}/terminal/{terminal}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和终端类型查询布局列表", description = "根据表单ID和终端类型查询布局列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "terminal", description = "终端类型", required = true)
    })
    public Flux<FormLayout> selectByFormIdAndTerminal(
            @PathVariable Long formId,
            @PathVariable Integer terminal) {
        return layoutService.selectByFormIdAndTerminal(formId, terminal);
    }

    /**
     * 根据表单ID查询默认布局
     */
    @GetMapping(value = "/form/{formId}/default", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID查询默认布局", description = "根据表单ID查询默认布局")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Mono<FormLayout> selectDefaultByFormId(@PathVariable Long formId) {
        return layoutService.selectDefaultByFormId(formId);
    }

    /**
     * 获取表单布局详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单布局详情", description = "根据ID获取表单布局详情")
    @Parameter(name = "id", description = "布局ID", required = true)
    public Mono<FormLayout> getById(@PathVariable Long id) {
        return layoutService.findById(id);
    }

    /**
     * 创建表单布局
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单布局", description = "创建新的表单布局")
    public Mono<FormLayout> create(@RequestBody FormLayout layout) {
        return layoutService.create(layout);
    }

    /**
     * 更新表单布局
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单布局", description = "更新表单布局信息")
    @Parameter(name = "id", description = "布局ID", required = true)
    public Mono<FormLayout> update(@PathVariable Long id, @RequestBody FormLayout layout) {
        layout.setId(id);
        return layoutService.update(layout);
    }

    /**
     * 删除表单布局
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单布局", description = "删除表单布局")
    @Parameter(name = "id", description = "布局ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return layoutService.delete(id);
    }

    /**
     * 设置默认布局
     */
    @PutMapping(value = "/{id}/default", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "设置默认布局", description = "设置默认布局")
    @Parameter(name = "id", description = "布局ID", required = true)
    public Mono<Boolean> setDefault(@PathVariable Long id) {
        return layoutService.setDefault(id);
    }
}