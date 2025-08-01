package org.apaas.auth.service;

import org.apaas.auth.form.RegisterBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录校验方法
 */
public interface SysLoginService {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    String login(String username, String password, String code, String uuid);

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态
     * @param message 消息内容
     * @param args 列表
     */
    void recordLoginInfo(String username, String status, String message, Object... args);

    /**
     * 退出登录
     *
     * @param loginToken 登录令牌
     */
    void logout(String loginToken);

    /**
     * 注册
     *
     * @param registerBody 注册信息
     * @return 结果
     */
    String register(RegisterBody registerBody);

    /**
     * 刷新令牌有效期
     *
     * @param loginToken 登录令牌
     */
    void refreshToken(String loginToken);

    /**
     * 获取请求token
     *
     * @param request 请求
     * @return token
     */
    String getToken(HttpServletRequest request);
}