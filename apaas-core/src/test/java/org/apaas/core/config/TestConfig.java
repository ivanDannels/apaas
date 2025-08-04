package org.apaas.core.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootConfiguration
@ComponentScan(basePackages = "org.apaas.core")
@PropertySource("classpath:application.yml")
public class TestConfig {
    // 这里可以添加测试所需的额外配置
}