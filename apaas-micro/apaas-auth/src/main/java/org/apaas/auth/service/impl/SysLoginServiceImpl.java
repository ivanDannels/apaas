package org.apaas.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.apaas.auth.form.RegisterBody;
import org.apaas.auth.service.SysLoginService;
import org.apaas.core.constant.Constants;
import org.apaas.core.exception.ServiceException;
import org.apaas.core.utils.IpUtils;
import org.apaas.core.utils.JwtUtils;
import org.apaas.core.utils.RedisUtils;
import org.apaas.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录校验方法
 */
@Service
@RequiredArgsConstructor
public class SysLoginServiceImpl implements SysLoginService {

    private final AuthenticationManager authenticationManager;
    private final RedisUtils redisUtils;
    private final PasswordEncoder passwordEncoder;

    @Value("${token.header}")
    private String header;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * 登录
     */
    @Override
    public String login(String username, String password, String code, String uuid) {
        // 验证码校验
        validateCaptcha(username, code, uuid);
        
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                recordLoginInfo(username, Constants.LOGIN_FAIL, "用户密码错误");
                throw new ServiceException("用户密码错误");
            } else {
                recordLoginInfo(username, Constants.LOGIN_FAIL, e.getMessage());
                throw new ServiceException(e.getMessage());
            }
        }
        
        recordLoginInfo(username, Constants.LOGIN_SUCCESS, "登录成功");
        
        // 生成token
        return createToken(authentication, username);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisUtils.getCacheObject(verifyKey);
        redisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLoginInfo(username, Constants.LOGIN_FAIL, "验证码已失效");
            throw new ServiceException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLoginInfo(username, Constants.LOGIN_FAIL, "验证码错误");
            throw new ServiceException("验证码错误");
        }
    }

    /**
     * 创建令牌
     */
    public String createToken(Authentication authentication, String username) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(Constants.JWT_USERNAME, username);
        claimsMap.put(Constants.JWT_CREATED, System.currentTimeMillis());
        
        return JwtUtils.createToken(claimsMap);
    }

    /**
     * 记录登录信息
     */
    @Override
    public void recordLoginInfo(String username, String status, String message, Object... args) {
        // TODO: 实现登录信息记录
        System.out.println(String.format("用户: %s, 状态: %s, 消息: %s", username, status, message));
    }

    /**
     * 退出登录
     */
    @Override
    public void logout(String loginToken) {
        if (StringUtils.isNotEmpty(loginToken)) {
            String username = JwtUtils.getUserName(loginToken);
            // 删除用户缓存记录
            redisUtils.deleteObject(getTokenKey(username));
        }
    }

    /**
     * 注册
     */
    @Override
    public String register(RegisterBody registerBody) {
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        
        // TODO: 实现用户注册逻辑
        // 1. 校验用户名是否存在
        // 2. 创建用户
        // 3. 返回注册结果
        
        return "注册成功";
    }

    /**
     * 刷新令牌有效期
     */
    @Override
    public void refreshToken(String loginToken) {
        String username = JwtUtils.getUserName(loginToken);
        String userKey = getTokenKey(username);
        redisUtils.expire(userKey, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 获取请求token
     */
    @Override
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String username) {
        return Constants.LOGIN_USER_KEY + username;
    }
}