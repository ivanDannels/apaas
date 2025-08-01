package org.apaas.gateway.filter.factory;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * 请求时间过滤器工厂
 */
@Slf4j
@Component
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {

    private static final String START_TIME = "startTime";

    public RequestTimeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                Long startTime = exchange.getAttribute(START_TIME);
                if (startTime != null) {
                    long executeTime = System.currentTimeMillis() - startTime;
                    log.info("Request {} {} executed in {}ms", exchange.getRequest().getMethod(), exchange.getRequest().getURI().getPath(), executeTime);
                }
            }));
        };
    }

    @Data
    public static class Config {
        private boolean enabled;
    }
}