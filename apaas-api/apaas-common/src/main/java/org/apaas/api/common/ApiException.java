package org.apaas.api.common;

import lombok.Getter;

/**
 * API异常类
 */
@Getter
public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 构造函数
     */
    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 构造函数，默认错误码为500
     */
    public ApiException(String message) {
        this(500, message);
    }

    /**
     * 构造函数，基于ApiResponse
     */
    public ApiException(ApiResponse<?> response) {
        this(response.getCode(), response.getMessage());
    }
}