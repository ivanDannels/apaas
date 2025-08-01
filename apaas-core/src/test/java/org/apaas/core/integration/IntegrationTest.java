package org.apaas.core.integration;

import org.apaas.core.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Import(TestConfig.class)
@Testcontainers
class IntegrationTest {
    
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
        
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://" + 
            postgresqlContainer.getHost() + ":" + postgresqlContainer.getFirstMappedPort() + 
            "/" + postgresqlContainer.getDatabaseName());
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }
    
    @Test
    void contextLoads() {
        // 这个测试确保应用程序上下文正确加载
    }
}