package org.apaas.design.platform.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.design.platform.entity.CodeTemplate;
import org.apaas.design.platform.repository.CodeTemplateRepository;
import org.apaas.design.platform.service.reactive.ReactiveCodeTemplateService;
import org.apaas.common.exception.BusinessException;
import org.apaas.common.web.domain.PageResult;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.core.web.domain.BasePageQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

/**
 * 响应式代码模板服务实现类
 */
@Service
@RequiredArgsConstructor
public class ReactiveCodeTemplateServiceImpl extends BaseServiceImpl<CodeTemplate, Long, CodeTemplateRepository> implements ReactiveCodeTemplateService {

    private final CodeTemplateRepository codeTemplateRepository;

    /**
     * 分页查询代码模板
     *
     * @param query 查询参数
     * @return 分页结果
     */
    @Override
    public Mono<PageResult<CodeTemplate>> selectCodeTemplatePage(BasePageQuery query) {
        PageRequest pageRequest = PageRequest.of(
                query.getPageNum() - 1,
                query.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createTime")
        );

        return codeTemplateRepository.findAll(pageRequest)
                .map(page -> new PageResult<>(query.getPageNum(), query.getPageSize(), page.getTotalElements(), page.getContent()));
    }

    /**
     * 根据模板名称查询代码模板
     *
     * @param templateName 模板名称
     * @return 代码模板
     */
    @Override
    public Mono<CodeTemplate> getCodeTemplateByTemplateName(String templateName) {
        return codeTemplateRepository.findByTemplateName(templateName);
    }

    /**
     * 根据模板类型查询代码模板列表
     *
     * @param templateType 模板类型
     * @return 代码模板列表
     */
    @Override
    public Flux<CodeTemplate> getCodeTemplatesByTemplateType(String templateType) {
        return codeTemplateRepository.findByTemplateType(templateType);
    }

    /**
     * 部署代码模板
     *
     * @param codeTemplate 代码模板
     * @return 部署结果
     */
    @Override
    public Mono<Boolean> deployCodeTemplate(CodeTemplate codeTemplate) {
        codeTemplate.setDeployed(true);
        codeTemplate.setDeployTime(new java.util.Date());
        return super.save(codeTemplate).map(saved -> true);
    }

    /**
     * 删除代码模板
     *
     * @param templateId 模板ID
     * @return 删除结果
     */
    @Override
    public Mono<Boolean> deleteCodeTemplate(Long templateId) {
        return super.findById(templateId)
                .switchIfEmpty(Mono.error(new BusinessException("代码模板不存在")))
                .flatMap(codeTemplate -> {
                    // 检查代码模板是否已部署
                    if (codeTemplate.getDeployed()) {
                        return Mono.error(new BusinessException("已部署的代码模板不能删除"));
                    }
                    return super.deleteById(templateId).map(deleted -> true);
                });
    }
}