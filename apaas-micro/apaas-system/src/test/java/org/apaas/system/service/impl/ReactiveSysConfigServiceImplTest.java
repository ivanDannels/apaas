package org.apaas.system.service.impl;

import org.apaas.system.entity.SysConfig;
import org.apaas.system.repository.SysConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReactiveSysConfigServiceImplTest {

    @Mock
    private SysConfigRepository repository;

    @InjectMocks
    private ReactiveSysConfigServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConfigPage() {
        // 准备测试数据
        SysConfig config1 = SysConfig.builder()
                .id(1L)
                .name("配置项1")
                .configKey("config.key.1")
                .code("CONFIG_KEY_1")
                .value("值1")
                .type(0)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        SysConfig config2 = SysConfig.builder()
                .id(2L)
                .name("配置项2")
                .configKey("config.key.2")
                .code("CONFIG_KEY_2")
                .value("值2")
                .type(0)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.findAll()).thenReturn(Flux.just(config1, config2));

        // 执行测试
        Pageable pageable = PageRequest.of(0, 10);
        Flux<SysConfig> result = service.getConfigPage(pageable, null);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(config1)
                .expectNext(config2)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findAll();
    }

    @Test
    void testAddConfig() {
        // 准备测试数据
        SysConfig config = SysConfig.builder()
                .id(1L)
                .name("配置项1")
                .configKey("config.key.1")
                .code("CONFIG_KEY_1")
                .value("值1")
                .type(0)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.save(any(SysConfig.class))).thenReturn(Mono.just(config));

        // 执行测试
        Mono<Boolean> result = service.addConfig(config);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).save(config);
    }

    @Test
    void testUpdateConfig() {
        // 准备测试数据
        SysConfig config = SysConfig.builder()
                .id(1L)
                .name("配置项1")
                .configKey("config.key.1")
                .code("CONFIG_KEY_1")
                .value("更新的值1")
                .type(0)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.save(any(SysConfig.class))).thenReturn(Mono.just(config));

        // 执行测试
        Mono<Boolean> result = service.updateConfig(config);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).save(config);
    }

    @Test
    void testDeleteConfig() {
        // 模拟Repository行为
        when(repository.deleteById(1L)).thenReturn(Mono.empty());

        // 执行测试
        Mono<Boolean> result = service.deleteConfig(1L);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testGetConfigByCode() {
        // 准备测试数据
        SysConfig config = SysConfig.builder()
                .id(1L)
                .name("配置项1")
                .configKey("config.key.1")
                .code("CONFIG_KEY_1")
                .value("值1")
                .type(0)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.findByCode("CONFIG_KEY_1")).thenReturn(Mono.just(config));

        // 执行测试
        Mono<SysConfig> result = service.getConfigByCode("CONFIG_KEY_1");

        // 验证结果
        StepVerifier.create(result)
                .expectNext(config)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findByCode("CONFIG_KEY_1");
    }
}