package org.apaas.core.integration;

import org.apaas.core.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(classes = TestConfig.class)
class IntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://10.100.20.61:5432/apaas");
        registry.add("spring.r2dbc.username", () -> "postgres");
        registry.add("spring.r2dbc.password", () -> "Postgresql@9527$");
        registry.add("spring.datasource.url", () -> "jdbc:postgresql:///10.100.20.61:5432/apaas");
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "Postgresql@9527$");
    }
    
    @Test
    void contextLoads() {
        System.out.println("Integration Test");
    }
}