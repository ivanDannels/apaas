package org.apaas.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import jakarta.validation.ConstraintViolationException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * @author ivan
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${logging.level.org.apaas:INFO}")
    private String logLevel;

    /**
     * 处理业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        HttpStatus status = code != null && code >= 400 && code < 600 ? 
                HttpStatus.valueOf(code) : HttpStatus.INTERNAL_SERVER_ERROR;

        String message = code != null ? e.getMessage() : "服务器内部错误，请联系管理员";
        String detailMessage = e.getDetailMessage();

        return buildErrorResponse(status, "Service Error", message, detailMessage);
    }

    /**
     * 处理权限不足异常
     */
//    @ExceptionHandler(AccessDeniedException.class)
//    public Mono<ResponseEntity<Map<String, Object>>> handleAccessDeniedException(AccessDeniedException e) {
//        log.error("权限不足", e);
//        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access Denied", "权限不足", e.getMessage());
//    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", message, e.getMessage());
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Binding Error", message, e.getMessage());
    }

    /**
     * 处理参数校验异常
     */
//    @ExceptionHandler(ConstraintViolationException.class)
//    public Mono<ResponseEntity<Map<String, Object>>> handleConstraintViolationException(ConstraintViolationException e) {
//        log.error(e.getMessage(), e);
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", e.getMessage(), e.getMessage());
//    }

    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleException(Exception ex) {
        log.error("系统异常: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "系统内部错误", ex.getMessage());
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("非法参数异常: ", ex);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), ex.getMessage());
    }

    /**
     * 构建错误响应
     */
    private Mono<ResponseEntity<Map<String, Object>>> buildErrorResponse(HttpStatus status, String error, String message, String detailMessage) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", error);
        errorResponse.put("message", message);

        if ("DEBUG".equalsIgnoreCase(logLevel) && detailMessage != null) {
            errorResponse.put("detail", detailMessage);
        }

        return Mono.just(ResponseEntity.status(status).body(errorResponse));
    }
}