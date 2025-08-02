package org.apaas.form;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 表单引擎服务
 */
@SpringBootApplication(scanBasePackages = "org.apaas")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.apaas")

public class FormEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FormEngineApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  表单引擎服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}