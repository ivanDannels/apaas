package org.apaas.system.service.impl;

import org.apaas.system.entity.Internationalization;
import org.apaas.system.repository.InternationalizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReactiveInternationalizationServiceImplTest {

    @Mock
    private InternationalizationRepository repository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private ReactiveInternationalizationServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMessage() {
        // 模拟MessageSource行为
        when(messageSource.getMessage(eq("test.code"), any(Object[].class), eq("test.code"), any(Locale.class)))
                .thenReturn("测试消息");

        // 执行测试
        Mono<String> result = service.getMessage("test.code");

        // 验证结果
        StepVerifier.create(result)
                .expectNext("测试消息")
                .verifyComplete();

        // 验证MessageSource方法被调用
        verify(messageSource, times(1)).getMessage(eq("test.code"), any(Object[].class), eq("test.code"), any(Locale.class));
    }

    @Test
    void testGetMessageWithArgs() {
        // 模拟MessageSource行为
        when(messageSource.getMessage(eq("test.code"), any(Object[].class), eq("test.code"), any(Locale.class)))
                .thenReturn("测试消息 {0}");

        // 执行测试
        Mono<String> result = service.getMessage("test.code", new Object[]{"参数1"});

        // 验证结果
        StepVerifier.create(result)
                .expectNext("测试消息 {0}")
                .verifyComplete();

        // 验证MessageSource方法被调用
        verify(messageSource, times(1)).getMessage(eq("test.code"), any(Object[].class), eq("test.code"), any(Locale.class));
    }

    @Test
    void testGetMessageWithLocale() {
        // 准备测试数据
        Locale locale = Locale.CHINA;

        // 模拟MessageSource行为
        when(messageSource.getMessage("test.code", null, "test.code", locale))
                .thenReturn("测试消息");

        // 执行测试
        Mono<String> result = service.getMessage("test.code", null, locale);

        // 验证结果
        StepVerifier.create(result)
                .expectNext("测试消息")
                .verifyComplete();

        // 验证MessageSource方法被调用
        verify(messageSource, times(1)).getMessage("test.code", null, "test.code", locale);
    }

    @Test
    void testSetLanguage() {
        // 执行测试
        Mono<Void> result = service.setLanguage("zh_CN");

        // 验证结果
        StepVerifier.create(result)
                .verifyComplete();
    }

    @Test
    void testGetCurrentLanguage() {
        // 执行测试
        Mono<String> result = service.getCurrentLanguage();

        // 验证结果
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetMessagesByLanguage() {
        // 准备测试数据
        Internationalization msg1 = Internationalization.builder()
                .id(1L)
                .code("test.code.1")
                .message("测试消息1")
                .language("zh_CN")
                .build();

        Internationalization msg2 = Internationalization.builder()
                .id(2L)
                .code("test.code.2")
                .message("测试消息2")
                .language("zh_CN")
                .build();

        // 模拟Repository行为
        when(repository.findAll()).thenReturn(Flux.just(msg1, msg2));

        // 执行测试
        Flux<Internationalization> result = service.getMessagesByLanguage("zh_CN");

        // 验证结果
        StepVerifier.create(result)
                .expectNext(msg1)
                .expectNext(msg2)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findAll();
    }
}