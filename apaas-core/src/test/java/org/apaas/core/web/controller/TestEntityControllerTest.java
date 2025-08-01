package org.apaas.core.web.controller;

import org.apaas.core.domain.TestEntity;
import org.apaas.core.service.TestEntityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebFluxTest(TestEntityController.class)
class TestEntityControllerTest {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @MockBean
    private TestEntityService testEntityService;
    
    @Test
    void testCreate() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 设置mock行为
        when(testEntityService.save(any(TestEntity.class))).thenReturn(Mono.just(entity));
        
        // 执行测试
        webTestClient.post()
                .uri("/api/test-entities")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"name\": \"Test Entity\", \"description\": \"Test Description\"}")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test Entity");
    }
    
    @Test
    void testFindById() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(1L);
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 设置mock行为
        when(testEntityService.findById(anyLong())).thenReturn(Mono.just(entity));
        
        // 执行测试
        webTestClient.get()
                .uri("/api/test-entities/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Test Entity");
    }
    
    @Test
    void testFindAll() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setId(1L);
        entity.setTenantId(1L);
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 设置mock行为
        when(testEntityService.findAll()).thenReturn(Flux.just(entity));
        
        // 执行测试
        webTestClient.get()
                .uri("/api/test-entities")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo(1)
                .jsonPath("$.[0].name").isEqualTo("Test Entity");
    }
    
    @Test
    void testDeleteById() {
        // 设置mock行为
        when(testEntityService.deleteById(anyLong())).thenReturn(Mono.empty());
        
        // 执行测试
        webTestClient.delete()
                .uri("/api/test-entities/1")
                .exchange()
                .expectStatus().isOk();
    }
}