package org.apaas.core.web.domain;

import java.util.HashMap;

/**
 * 统一返回结果
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "message";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /** 异常信息 */
    public static final String DETAIL_MESSAGE_TAG = "detailMessage";

    /** 状态类型 */
    public enum Type {
        /** 成功 */
        SUCCESS(200),
        /** 警告 */
        WARN(301),
        /** 错误 */
        ERROR(500);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    /** 初始化一个新创建的 AjaxResult 对象 */
    public AjaxResult() {
    }

    /** 初始化一个新创建的 AjaxResult 对象
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /** 初始化一个新创建的 AjaxResult 对象
     * @param code 状态码
     * @param msg 返回内容
     * @param detailMessage 异常信息
     */
    public AjaxResult(int code, String msg, String detailMessage) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        super.put(DETAIL_MESSAGE_TAG, detailMessage);
    }

    /** 初始化一个新创建的 AjaxResult 对象
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (data != null) {
            super.put(DATA_TAG, data);
        }
    }

    /** 返回成功消息 */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /** 返回成功数据
     * @param data 数据对象
     */
    public static AjaxResult success(Object data) {
        return AjaxResult.success("操作成功", data);
    }

    /** 返回成功消息
     * @param msg 返回内容
     */
    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    /** 返回成功消息
     * @param msg 返回内容
     * @param data 数据对象
     */
    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(Type.SUCCESS.value(), msg, data);
    }

    /** 返回错误消息 */
    public static AjaxResult error() {
        return AjaxResult.error("操作失败");
    }

    /** 返回错误消息
     * @param msg 返回内容
     */
    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    /** 返回错误消息
     * @param msg 返回内容
     * @param detailMessage 异常信息
     */
    public static AjaxResult error(String msg, String detailMessage) {
        return new AjaxResult(Type.ERROR.value(), msg, detailMessage);
    }

    /** 返回错误消息
     * @param code 状态码
     * @param msg 返回内容
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg);
    }

    /** 返回警告消息
     * @param msg 返回内容
     */
    public static AjaxResult warn(String msg) {
        return AjaxResult.warn(msg, null);
    }

    /** 返回警告消息
     * @param msg 返回内容
     * @param data 数据对象
     */
    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(Type.WARN.value(), msg, data);
    }

    /** 返回详细错误信息
     * @param msg 返回内容
     * @param detailMessage 异常信息
     */
    public static AjaxResult detailError(String msg, String detailMessage) {
        return new AjaxResult(Type.ERROR.value(), msg, detailMessage);
    }

    /** 返回详细警告信息
     * @param msg 返回内容
     * @param detailMessage 异常信息
     */
    public static AjaxResult detailWarn(String msg, String detailMessage) {
        return new AjaxResult(Type.WARN.value(), msg, detailMessage);
    }

    /** 方便链式调用
     * @param key 键
     * @param value 值
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}