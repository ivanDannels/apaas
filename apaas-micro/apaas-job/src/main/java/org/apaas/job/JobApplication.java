package org.apaas.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 调度任务服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableReactiveFeignClients(basePackages = {"org.apaas"})
@ComponentScan(basePackages = {"org.apaas"})
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}