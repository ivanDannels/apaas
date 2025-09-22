package org.apaas.core.config;

import org.apaas.core.web.domain.AjaxResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一响应结果包装处理
 */
@RestControllerAdvice
public class ResponseWrapperAdvice {

    // 在WebFlux中，我们不使用ResponseBodyAdvice
    // 而是使用WebFilter或其他方式来处理响应包装
}