package org.apaas.core.service.impl;

import org.apaas.core.service.DomainService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 示例领域服务实现
 */
@Service
public class ExampleDomainService implements DomainService<String> {
    
    @Override
    public Mono<String> execute(String domainObject) {
        // 模拟一些业务逻辑处理
        return Mono.just("Processed: " + domainObject);
    }
}