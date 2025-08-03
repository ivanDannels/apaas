package org.apaas.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统服务启动类
 * @author ivan
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveFeignClients(basePackages = {"org.apaas"})
@ComponentScan(basePackages = {"org.apaas"})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}