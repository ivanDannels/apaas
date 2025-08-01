package org.apaas.core.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "org.apaas.core")
public class TestConfig {
    // 这里可以添加测试所需的额外配置
}