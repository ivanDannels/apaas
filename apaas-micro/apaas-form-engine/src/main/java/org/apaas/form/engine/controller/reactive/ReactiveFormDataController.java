package org.apaas.form.engine.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.core.domain.Page;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.form.domain.entity.FormData;
import org.apaas.form.engine.service.reactive.ReactiveFormDataService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式表单数据控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/form-data")
@Tag(name = "响应式表单数据管理", description = "响应式表单数据相关操作")
@RequiredArgsConstructor
public class ReactiveFormDataController {

    private final ReactiveFormDataService formDataService;

    /**
     * 获取表单数据列表
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单数据列表", description = "分页查询表单数据信息")
    public Mono<Page<FormData>> list(BasePageQuery query) {
        return formDataService.selectFormDataPage(query);
    }

    /**
     * 根据表单编码获取表单数据列表
     */
    @GetMapping(value = "/form/{formCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单编码获取表单数据列表", description = "查询指定表单的所有数据")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    public Flux<FormData> getByFormCode(@PathVariable String formCode) {
        return formDataService.getFormDataByFormCode(formCode);
    }

    /**
     * 根据表单编码和版本获取表单数据列表
     */
    @GetMapping(value = "/form/{formCode}/version/{version}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据表单编码和版本获取表单数据列表", description = "查询指定表单版本的所有数据")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    @Parameter(name = "version", description = "表单版本", required = true)
    public Flux<FormData> getByFormCodeAndVersion(@PathVariable String formCode, @PathVariable String version) {
        return formDataService.getFormDataByFormCodeAndVersion(formCode, version);
    }

    /**
     * 根据业务键获取表单数据
     */
    @GetMapping(value = "/business-key/{businessKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "根据业务键获取表单数据", description = "查询指定业务键的表单数据")
    @Parameter(name = "businessKey", description = "业务键", required = true)
    public Mono<FormData> getByBusinessKey(@PathVariable String businessKey) {
        return formDataService.getFormDataByBusinessKey(businessKey);
    }

    /**
     * 获取表单数据详情
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单数据详情", description = "根据ID查询表单数据信息")
    @Parameter(name = "id", description = "表单数据ID", required = true)
    public Mono<FormData> getById(@PathVariable Long id) {
        return formDataService.findById(id);
    }

    /**
     * 保存表单数据（草稿状态）
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "保存表单数据", description = "保存表单数据为草稿状态")
    public Mono<Long> save(@RequestBody FormData formData) {
        return formDataService.saveFormData(formData);
    }

    /**
     * 提交表单数据
     */
    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "提交表单数据", description = "提交表单数据为已提交状态")
    public Mono<Long> submit(@RequestBody FormData formData) {
        return formDataService.submitFormData(formData);
    }

    /**
     * 更新表单数据
     */
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新表单数据", description = "修改表单数据信息")
    public Mono<Boolean> update(@RequestBody FormData formData) {
        return formDataService.updateFormData(formData);
    }

    /**
     * 删除表单数据
     */
    @DeleteMapping(value = "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除表单数据", description = "批量删除表单数据")
    @Parameter(name = "ids", description = "表单数据ID数组", required = true)
    public Mono<Boolean> remove(@PathVariable Long[] ids) {
        return formDataService.deleteFormData(ids);
    }

    /**
     * 批量导入表单数据
     */
    @PostMapping(value = "/import/{formCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量导入表单数据", description = "从JSON数据导入表单数据")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    public Mono<Boolean> importData(@PathVariable String formCode, @RequestBody String dataJson) {
        return formDataService.importFormData(formCode, dataJson);
    }

    /**
     * 获取表单数据统计
     */
    @GetMapping(value = "/statistics/{formCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取表单数据统计", description = "统计表单数据信息")
    @Parameter(name = "formCode", description = "表单编码", required = true)
    public Mono<Object> getStatistics(@PathVariable String formCode) {
        return formDataService.getFormDataStatistics(formCode);
    }

    /**
     * 验证表单数据
     */
    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "验证表单数据", description = "验证表单数据是否有效")
    public Mono<Boolean> validate(@RequestBody FormData formData) {
        return formDataService.validateFormData(formData);
    }
}