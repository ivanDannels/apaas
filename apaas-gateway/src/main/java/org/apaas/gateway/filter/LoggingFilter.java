package org.apaas.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志过滤器
 */
@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        String method = request.getMethod().name();
        HttpHeaders headers = request.getHeaders();

        // 记录请求开始时间
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());

        // 打印请求日志
        log.debug("Request: {} {}, Headers: {}", method, path, formatHeaders(headers));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 计算请求耗时
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {
                long executeTime = System.currentTimeMillis() - startTime;
                log.debug("Response: {} {} completed in {}ms, Status: {}", method, path, executeTime, exchange.getResponse().getStatusCode());
            }
        }));
    }

    @Override
    public int getOrder() {
        return -200;
    }

    /**
     * 格式化请求头
     */
    private String formatHeaders(HttpHeaders headers) {
        List<String> headerList = new ArrayList<>();
        headers.forEach((name, values) -> {
            // 敏感信息不打印
            if (StringUtils.equalsIgnoreCase(name, "Authorization") ||
                    StringUtils.equalsIgnoreCase(name, "Cookie")) {
                headerList.add(name + ": ******");
            } else {
                headerList.add(name + ": " + StringUtils.join(values, ","));
            }
        });
        return StringUtils.join(headerList, ", ");
    }
}