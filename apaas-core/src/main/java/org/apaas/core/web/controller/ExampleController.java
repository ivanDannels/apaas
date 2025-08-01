package org.apaas.core.web.controller;

import org.apaas.core.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 示例控制器
 */
@RestController
@RequestMapping("/api/examples")
public class ExampleController {
    
    @Autowired
    private DomainService<String> exampleDomainService;
    
    /**
     * 处理示例请求
     * @param input 输入参数
     * @return 处理结果
     */
    @PostMapping("/process")
    public Mono<String> process(@RequestBody String input) {
        return exampleDomainService.execute(input);
    }
}