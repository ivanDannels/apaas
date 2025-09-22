package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.web.controller.ReactiveBaseController;
import org.apaas.system.entity.SysOperLog;
import org.apaas.system.service.reactive.ReactiveSysOperLogService;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * 操作日志记录
 * @author ivan
 */
@Slf4j
@Tag(name = "操作日志管理")
@RestController
@RequestMapping("/api/v1/reactive/oper-logs")
public class SysOperLogController extends ReactiveBaseController<SysOperLog, Long, ReactiveSysOperLogService> {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public SysOperLogController(ReactiveSysOperLogService service, ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        super(service);
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    // 测试缓存
    @GetMapping("/cache")
    public Mono<Object> testCache() {
        reactiveRedisTemplate.opsForValue().set("test", "test")
                .subscribe(value -> log.debug("Set test cache : {}", value),
                        error -> log.error("Error in test cache", error),
                        () -> log.debug("Test cache completed"));
        return reactiveRedisTemplate.opsForValue().get("test");
    }

}