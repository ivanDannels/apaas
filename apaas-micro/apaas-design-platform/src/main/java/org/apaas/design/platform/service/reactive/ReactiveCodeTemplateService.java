package org.apaas.design.platform.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.design.platform.entity.CodeTemplate;
import org.apaas.core.web.domain.BasePageQuery;
import org.apaas.core.query.PageResult;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式代码模板服务接口
 */
public interface ReactiveCodeTemplateService extends BaseService<CodeTemplate, Long> {

    /**
     * 分页查询代码模板
     *
     * @param query 查询参数
     * @return 分页结果
     */
    Mono<PageResult<CodeTemplate>> selectCodeTemplatePage(BasePageQuery query);

    /**
     * 根据模板名称查询代码模板
     *
     * @param templateName 模板名称
     * @return 代码模板
     */
    Mono<CodeTemplate> getCodeTemplateByTemplateName(String templateName);

    /**
     * 根据模板类型查询代码模板列表
     *
     * @param templateType 模板类型
     * @return 代码模板列表
     */
    Flux<CodeTemplate> getCodeTemplatesByTemplateType(String templateType);

    /**
     * 部署代码模板
     *
     * @param codeTemplate 代码模板
     * @return 部署结果
     */
    Mono<Boolean> deployCodeTemplate(CodeTemplate codeTemplate);

    /**
     * 删除代码模板
     *
     * @param templateId 模板ID
     * @return 删除结果
     */
    Mono<Boolean> deleteCodeTemplate(Long templateId);
}