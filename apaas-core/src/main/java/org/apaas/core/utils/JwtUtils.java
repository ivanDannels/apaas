package org.apaas.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.constant.Constants;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JWT工具类
 */
@Slf4j
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 密钥
     */
    private static String secret = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 令牌有效期（默认30分钟）
     */
    private static long expireTime = 30 * 60 * 1000;

    /**
     * 设置令牌有效期
     */
    public static void setExpireTime(long expireTime) {
        JwtUtils.expireTime = expireTime;
    }

    /**
     * 设置密钥
     */
    public static void setSecret(String secret) {
        JwtUtils.secret = secret;
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expirationDate = new Date(nowMillis + expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("解析令牌失败", e);
            return null;
        }
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 从令牌中获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? Long.valueOf(claims.get(Constants.JWT_USERID).toString()) : null;
    }

    /**
     * 验证令牌有效期
     *
     * @param token 令牌
     * @return 是否有效
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return true;
            }
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}