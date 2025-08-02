package org.apaas.system.controller.reactive;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apaas.system.service.reactive.ReactiveInternationalizationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

/**
 * 国际化控制器
 */
@RestController
@RequestMapping("/api/v1/reactive/i18n")
@Tag(name = "国际化管理", description = "国际化管理API")
@RequiredArgsConstructor
public class InternationalizationController {

    private final ReactiveInternationalizationService i18nService;

    /**
     * 切换语言
     *
     * @param language 语言代码，如zh_CN、en等
     * @return 结果
     */
    @GetMapping(value = "/changeLanguage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "切换语言", description = "切换系统语言")
    @Parameter(name = "language", description = "语言代码，如zh_CN、en等", required = true)
    public Mono<Boolean> changeLanguage(@RequestParam String language) {
        return i18nService.setLanguage(language)
                .onErrorReturn(false);
    }

    /**
     * 获取当前语言
     *
     * @return 当前语言代码
     */
    @GetMapping(value = "/currentLanguage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取当前语言", description = "获取当前系统语言")
    public Mono<String> getCurrentLanguage() {
        return i18nService.getCurrentLanguage();
    }

    /**
     * 获取国际化消息
     *
     * @param code 消息键
     * @return 消息内容
     */
    @GetMapping(value = "/getMessage", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "获取国际化消息", description = "根据消息键获取当前语言的消息")
    @Parameter(name = "code", description = "消息键", required = true)
    public Mono<String> getMessage(@RequestParam String code) {
        return i18nService.getMessage(code)
                .defaultIfEmpty("Message not found: " + code);
    }

    /**
     * 批量获取国际化消息
     *
     * @param codes 消息键数组
     * @return 消息内容映射
     */
    @PostMapping(value = "/getMessages", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "批量获取国际化消息", description = "批量获取国际化消息")
    @Parameter(name = "codes", description = "消息键数组", required = true)
    public Mono<Map<String, String>> getMessages(@RequestBody String[] codes) {
        return Mono.fromCallable(() -> {
            Map<String, String> messages = new HashMap<>();
            for (String code : codes) {
                messages.put(code, i18nService.getMessage(code).block());
            }
            return messages;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}