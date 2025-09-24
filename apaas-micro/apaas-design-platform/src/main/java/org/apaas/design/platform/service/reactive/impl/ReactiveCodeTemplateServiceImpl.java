package org.apaas.design.platform.service.reactive.impl;

import org.apaas.design.platform.entity.CodeTemplate;
import org.apaas.design.platform.repository.CodeTemplateRepository;
import org.apaas.design.platform.service.reactive.ReactiveCodeTemplateService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式代码模板服务实现类
 */
@Service
public class ReactiveCodeTemplateServiceImpl extends BaseServiceImpl<CodeTemplate, Long, CodeTemplateRepository> implements ReactiveCodeTemplateService {

    public ReactiveCodeTemplateServiceImpl(CodeTemplateRepository repository) {
        super(repository);
    }
}