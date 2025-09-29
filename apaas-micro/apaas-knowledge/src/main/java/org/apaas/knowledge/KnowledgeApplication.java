package org.apaas.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 知识库服务的Spring Boot应用程序入口
 */
@SpringBootApplication(scanBasePackages = "org.apaas.knowledge")
@EnableFeignClients(basePackages = "org.apaas.knowledge.infrastructure.client")
public class KnowledgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowledgeApplication.class, args);
    }

}