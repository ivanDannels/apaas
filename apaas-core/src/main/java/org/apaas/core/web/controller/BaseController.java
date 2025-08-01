package org.apaas.core.web.controller;

import org.apaas.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器基类
 */
@RestController
public class BaseController {
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return AjaxResult
     */
    protected AjaxResult success(String msg) {
        return AjaxResult.success(msg);
    }

    /**
     * 返回成功数据
     *
     * @param data 数据对象
     * @return AjaxResult
     */
    protected AjaxResult success(Object data) {
        return AjaxResult.success(data);
    }

    /**
     * 返回成功消息和数据
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return AjaxResult
     */
    protected AjaxResult success(String msg, Object data) {
        return AjaxResult.success(msg, data);
    }
    
    // 基类可以包含一些通用的方法或属性
}