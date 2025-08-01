package org.apaas.report.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "apaas-flow-engine", url = "http://localhost:8082")
public interface ProcessServiceClient {
    
    @GetMapping("/processes/{id}")
    Mono<String> getProcessById(@PathVariable("id") Long id);
}