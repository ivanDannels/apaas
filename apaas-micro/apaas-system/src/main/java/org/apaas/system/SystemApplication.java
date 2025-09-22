package org.apaas.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 系统服务启动类
 * @author ivan
 */
@SpringBootApplication(scanBasePackages = {"org.apaas"})
@ComponentScan(basePackages = {"org.apaas"})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}