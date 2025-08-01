package org.apaas.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 流程引擎服务
 */
@SpringBootApplication(scanBasePackages = "org.apaas")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.apaas")
@MapperScan("org.apaas.flow.mapper")
public class FlowEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowEngineApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  流程引擎服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}