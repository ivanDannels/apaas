package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.form.engine.entity.FormFieldPermission;
import org.apaas.form.engine.service.reactive.ReactiveFormFieldPermissionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单字段权限控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-field-permissions")
@Tag(name = "响应式表单字段权限管理", description = "响应式表单字段权限相关操作")
public class ReactiveFormFieldPermissionController extends ReactiveBaseController<FormFieldPermission, Long, ReactiveFormFieldPermissionService> {

    public ReactiveFormFieldPermissionController(ReactiveFormFieldPermissionService service) {
        super(service);
    }

    /**
     * 分页查询表单字段权限
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "分页查询表单字段权限", description = "分页查询表单字段权限列表")
    @Parameters({
            @Parameter(name = "formId", description = "表单ID", required = true),
            @Parameter(name = "page", description = "页码", required = true),
            @Parameter(name = "size", description = "每页条数", required = true)
    })
    public Flux<FormFieldPermission> selectPage(
            @RequestParam Long formId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.selectPage(formId, PageRequest.of(page, size));
    }

    /**
     * 根据ID查询表单字段权限
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据ID查询表单字段权限", description = "根据ID查询表单字段权限信息")
    @Parameter(name = "id", description = "权限ID", required = true)
    public Mono<FormFieldPermission> selectById(@PathVariable Long id) {
        return service.findById(id);
    }

    /**
     * 根据表单ID查询字段权限列表
     */
    @GetMapping(value = "/form/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID查询字段权限列表", description = "根据表单ID查询所有字段权限列表")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormFieldPermission> selectByFormId(@PathVariable Long formId) {
        return service.selectByFormId(formId);
    }

    /**
     * 根据字段ID查询字段权限列表
     */
    @GetMapping(value = "/field/{fieldId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字段ID查询字段权限列表", description = "根据字段ID查询所有字段权限列表")
    @Parameter(name = "fieldId", description = "字段ID", required = true)
    public Flux<FormFieldPermission> selectByFieldId(@PathVariable Long fieldId) {
        return service.selectByFieldId(fieldId);
    }

    /**
     * 根据角色ID查询字段权限列表
     */
    @GetMapping(value = "/role/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据角色ID查询字段权限列表", description = "根据角色ID查询所有字段权限列表")
    @Parameter(name = "roleId", description = "角色ID", required = true)
    public Flux<FormFieldPermission> selectByRoleId(@PathVariable Long roleId) {
        return service.selectByRoleId(roleId);
    }

    /**
     * 根据表单ID和角色ID查询字段权限列表
     */
    @GetMapping(value = "/form/{formId}/role/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单ID和角色ID查询字段权限列表", description = "根据表单ID和角色ID查询字段权限列表")
    @Parameters({
            @Parameter(name = "formId", description = "表单ID", required = true),
            @Parameter(name = "roleId", description = "角色ID", required = true)
    })
    public Flux<FormFieldPermission> selectByFormIdAndRoleId(
            @PathVariable Long formId,
            @PathVariable Long roleId) {
        return service.selectByFormIdAndRoleId(formId, roleId);
    }

    /**
     * 根据字段ID和角色ID查询字段权限
     */
    @GetMapping(value = "/field/{fieldId}/role/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据字段ID和角色ID查询字段权限", description = "根据字段ID和角色ID查询字段权限")
    @Parameters({
            @Parameter(name = "fieldId", description = "字段ID", required = true),
            @Parameter(name = "roleId", description = "角色ID", required = true)
    })
    public Mono<FormFieldPermission> selectByFieldIdAndRoleId(
            @PathVariable Long fieldId,
            @PathVariable Long roleId) {
        return service.selectByFieldIdAndRoleId(fieldId, roleId);
    }

    /**
     * 创建表单字段权限
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "创建表单字段权限", description = "创建新的表单字段权限")
    public Mono<FormFieldPermission> create(@RequestBody FormFieldPermission fieldPermission) {
        return service.create(fieldPermission);
    }

    /**
     * 更新表单字段权限
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单字段权限", description = "更新表单字段权限信息")
    @Parameter(name = "id", description = "权限ID", required = true)
    public Mono<FormFieldPermission> update(@PathVariable Long id, @RequestBody FormFieldPermission fieldPermission) {
        fieldPermission.setId(id);
        return service.update(fieldPermission);
    }

    /**
     * 删除表单字段权限
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单字段权限", description = "删除表单字段权限")
    @Parameter(name = "id", description = "权限ID", required = true)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    /**
     * 批量创建表单字段权限
     */
    @PostMapping(value = "/batch/{formId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量创建表单字段权限", description = "批量创建表单字段权限")
    @Parameter(name = "formId", description = "表单ID", required = true)
    public Flux<FormFieldPermission> batchCreate(
            @PathVariable Long formId,
            @RequestBody Flux<FormFieldPermission> fieldPermissions) {
        return service.batchCreate(formId, fieldPermissions);
    }
}