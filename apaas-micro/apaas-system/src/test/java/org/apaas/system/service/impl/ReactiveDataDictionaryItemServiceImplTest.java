package org.apaas.system.service.impl;

import org.apaas.system.entity.DataDictionaryItem;
import org.apaas.system.repository.DataDictionaryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReactiveDataDictionaryItemServiceImplTest {

    @Mock
    private DataDictionaryItemRepository repository;

    @InjectMocks
    private ReactiveDataDictionaryItemServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSelectPage() {
        // 准备测试数据
        DataDictionaryItem item1 = DataDictionaryItem.builder()
                .id(1L)
                .dictId(1L)
                .name("测试项1")
                .code("TEST_ITEM_1")
                .value("测试值1")
                .sort(1)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        DataDictionaryItem item2 = DataDictionaryItem.builder()
                .id(2L)
                .dictId(1L)
                .name("测试项2")
                .code("TEST_ITEM_2")
                .value("测试值2")
                .sort(2)
                .status(1)
                .createdTime(LocalDateTime.now())
                .build();

        // 模拟Repository行为
        when(repository.findAll()).thenReturn(Flux.just(item1, item2));

        // 执行测试
        Flux<DataDictionaryItem> result = service.selectPage(1L);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(item1)
                .expectNext(item2)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findAll();
    }

    @Test
    void testExportExcel() {
        // 模拟Repository行为
        when(repository.findAll()).thenReturn(Flux.empty());

        // 执行测试
        Mono<Void> result = service.exportExcel(null, 1L);

        // 验证结果
        StepVerifier.create(result)
                .verifyComplete();

        // 验证Repository方法被调用
        verify(repository, times(1)).findAll();
    }

    @Test
    void testImportExcel() {
        // 准备测试数据
        byte[] fileData = new byte[0];

        // 执行测试
        Mono<Boolean> result = service.importExcel(fileData, 1L);

        // 验证结果
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }
}