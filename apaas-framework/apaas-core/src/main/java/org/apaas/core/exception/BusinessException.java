package org.apaas.core.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 业务异常
 * @author ivan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public BusinessException() {
    }

    /**
     * 构造方法
     *
     * @param message 错误提示
     */
    public BusinessException(String message) {
        this.code = 500;
        this.message = message;
    }

    /**
     * 构造方法
     *
     * @param message 错误提示
     * @param code    错误码
     */
    public BusinessException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    /**
     * 构造方法
     *
     * @param message       错误提示
     * @param detailMessage 错误明细
     */
    public BusinessException(String message, String detailMessage) {
        this.code = 500;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    /**
     * 构造方法
     *
     * @param message       错误提示
     * @param code          错误码
     * @param detailMessage 错误明细
     */
    public BusinessException(String message, Integer code, String detailMessage) {
        this.message = message;
        this.code = code;
        this.detailMessage = detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 创建一个业务异常
     *
     * @param message 错误提示
     * @return 业务异常
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

}