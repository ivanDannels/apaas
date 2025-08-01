package org.apaas.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apaas.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${logging.level.org.apaas:INFO}")
    private String logLevel;

    /**
     * 处理业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return code != null ? AjaxResult.detailError(e.getMessage(), e.getDetailMessage()) : AjaxResult.detailError("服务器内部错误，请联系管理员", e.getDetailMessage());
        }
        return code != null ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String message = "不支持' " + e.getMethod() + "'请求";
        log.error(message, e);
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return AjaxResult.detailError(message, "请求方法不支持");
        }
        return AjaxResult.error(message);
    }

    /**
     * 处理权限不足异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限不足", e);
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return AjaxResult.detailError("权限不足", e.getMessage());
        }
        return AjaxResult.error(403, "权限不足");
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return AjaxResult.detailError(message, e.getMessage());
        }
        return AjaxResult.error(message);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return AjaxResult.detailError(message, e.getMessage());
        }
        return AjaxResult.error(message);
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public AjaxResult handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return AjaxResult.detailError(e.getMessage(), e.getMessage());
        }
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 处理系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        if ("DEBUG".equalsIgnoreCase(logLevel)) {
            return AjaxResult.detailError("服务器内部错误，请联系管理员", e.getMessage());
        }
        return AjaxResult.error("服务器内部错误，请联系管理员");
    }
}