package org.apaas.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableReactiveWebFlux;

/**
 * 认证服务启动类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableReactiveFeignClients(basePackages = {"org.apaas"})
@ComponentScan(basePackages = {"org.apaas"})
@EnableReactiveWebFlux
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}