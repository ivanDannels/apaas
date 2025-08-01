package org.apaas.integration.utils;

import reactor.core.publisher.Mono;

public class AjaxResult<T> {
    private int code;
    private String msg;
    private T data;
    
    public AjaxResult() {}
    
    public AjaxResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    
    public static <T> Mono<AjaxResult<T>> success(T data) {
        return Mono.just(new AjaxResult<>(200, "success", data));
    }
    
    public static <T> Mono<AjaxResult<T>> error(String msg) {
        return Mono.just(new AjaxResult<>(500, msg, null));
    }
    
    // getters and setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}