package org.apaas.form.engine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apaas.form.engine.entity.FormField;
import org.apaas.form.engine.service.FormFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 表单字段控制器
 */
@RestController
@RequestMapping("/api/v1/form-fields")
@Tag(name = "表单字段管理", description = "表单字段相关操作")
public class FormFieldController {

    @Autowired
    private FormFieldService formFieldService;

    /**
     * 分页查询表单字段
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询表单字段", description = "根据表单ID分页查询表单字段列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "pageNum", description = "页码", required = true),
        @Parameter(name = "pageSize", description = "每页条数", required = true)
    })
    public Mono<IPage<FormField>> selectPage(
            @RequestParam Long formId,
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize) {
        IPage<FormField> page = formFieldService.selectPage(formId, pageNum, pageSize);
        return Mono.just(page);
    }

    /**
     * 根据表单ID查询字段列表
     */
    @GetMapping(value = "/form/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID查询字段列表", description = "根据表单ID查询所有字段列表")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Mono<List<FormField>> selectByFormId(@PathVariable Long formId) {
        List<FormField> fields = formFieldService.selectByFormId(formId);
        return Mono.just(fields);
    }

    /**
     * 根据表单ID和字段类型查询字段列表
     */
    @GetMapping(value = "/form/{formId}/type/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和字段类型查询字段列表", description = "根据表单ID和字段类型查询字段列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "type", description = "字段类型", required = true)
    })
    public Mono<List<FormField>> selectByFormIdAndType(
            @PathVariable Long formId,
            @PathVariable Integer type) {
        List<FormField> fields = formFieldService.selectByFormIdAndType(formId, type);
        return Mono.just(fields);
    }

    /**
     * 根据表单ID和分组名称查询字段列表
     */
    @GetMapping(value = "/form/{formId}/group/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和分组名称查询字段列表", description = "根据表单ID和分组名称查询字段列表")
    @Parameters({
        @Parameter(name = "formId", description = "表单ID", required = true),
        @Parameter(name = "groupName", description = "分组名称", required = true)
    })
    public Mono<List<FormField>> selectByFormIdAndGroupName(
            @PathVariable Long formId,
            @PathVariable String groupName) {
        List<FormField> fields = formFieldService.selectByFormIdAndGroupName(formId, groupName);
        return Mono.just(fields);
    }

    /**
     * 获取表单字段详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单字段详情", description = "根据ID获取表单字段详情")
    @Parameter(name = "id", description = "字段ID", required = true)
    public Mono<FormField> getById(@PathVariable Long id) {
        FormField field = formFieldService.getById(id);
        return Mono.just(field);
    }

    /**
     * 创建表单字段
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单字段", description = "创建新的表单字段")
    public Mono<Boolean> create(@RequestBody FormField formField) {
        boolean result = formFieldService.create(formField);
        return Mono.just(result);
    }

    /**
     * 更新表单字段
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单字段", description = "更新表单字段信息")
    @Parameter(name = "id", description = "字段ID", required = true)
    public Mono<Boolean> update(@PathVariable Long id, @RequestBody FormField formField) {
        formField.setId(id);
        boolean result = formFieldService.update(formField);
        return Mono.just(result);
    }

    /**
     * 删除表单字段
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单字段", description = "删除表单字段")
    @Parameter(name = "id", description = "字段ID", required = true)
    public Mono<Boolean> delete(@PathVariable Long id) {
        boolean result = formFieldService.delete(id);
        return Mono.just(result);
    }

    /**
     * 批量创建表单字段
     */
    @PostMapping(value = "/batch/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量创建表单字段", description = "批量创建表单字段")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Mono<Boolean> batchCreate(@PathVariable Long formId, @RequestBody List<FormField> formFields) {
        boolean result = formFieldService.batchCreate(formId, formFields);
        return Mono.just(result);
    }
}