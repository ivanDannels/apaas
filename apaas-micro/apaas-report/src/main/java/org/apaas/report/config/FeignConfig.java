package org.apaas.report.config;

import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients(basePackages = "org.apaas.report.feign")
public class FeignConfig {
}