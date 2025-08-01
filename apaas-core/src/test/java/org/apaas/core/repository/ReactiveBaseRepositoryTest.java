package org.apaas.core.repository;

import org.apaas.core.domain.TestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Testcontainers
class ReactiveBaseRepositoryTest {
    
    @Container
    static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://" + 
            postgresqlContainer.getHost() + ":" + postgresqlContainer.getFirstMappedPort() + 
            "/" + postgresqlContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", postgresqlContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresqlContainer::getPassword);
    }
    
    @Autowired
    private TestEntityRepository testEntityRepository;
    
    @Test
    void testSaveAndFind() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 保存实体
        TestEntity savedEntity = testEntityRepository.save(entity)
                .as(StepVerifier::create)
                .expectNextMatches(saved -> {
                    assertNotNull(saved.getId());
                    assertEquals("Test Entity", saved.getName());
                    return true;
                })
                .verifyComplete();
        
        // 根据ID查找
        testEntityRepository.findById(savedEntity.getId())
                .as(StepVerifier::create)
                .expectNextMatches(found -> {
                    assertEquals(savedEntity.getId(), found.getId());
                    assertEquals("Test Entity", found.getName());
                    return true;
                })
                .verifyComplete();
    }
    
    @Test
    void testFindAllByTenantIdAndDeletedFalse() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 保存实体
        testEntityRepository.save(entity).block();
        
        // 查找所有未删除的实体
        testEntityRepository.findAllByTenantIdAndDeletedFalse(1L)
                .as(StepVerifier::create)
                .expectNextMatches(found -> {
                    assertEquals("Test Entity", found.getName());
                    return true;
                })
                .verifyComplete();
    }
    
    @Test
    void testDeleteByIdAndTenantId() {
        // 创建测试实体
        TestEntity entity = new TestEntity();
        entity.setTenantId(1L);
        entity.setCreator("testUser");
        entity.setCreatedTime(LocalDateTime.now());
        entity.setUpdater("testUser");
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setName("Test Entity");
        entity.setDescription("Test Description");
        
        // 保存实体
        TestEntity savedEntity = testEntityRepository.save(entity).block();
        
        // 删除实体
        testEntityRepository.deleteByIdAndTenantId(savedEntity.getId(), 1L)
                .as(StepVerifier::create)
                .verifyComplete();
        
        // 验证实体已被删除
        testEntityRepository.findById(savedEntity.getId())
                .as(StepVerifier::create)
                .verifyComplete();
    }
}