package org.apaas.common.constant;

/**
 * 安全相关常量
 */
public class SecurityConstants {
    /**
     * 认证请求头名称
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Bearer前缀
     */
    public static final String BEARER_PREFIX = "Bearer ";

    /**
     * 令牌黑名单前缀
     */
    public static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    /**
     * 用户权限前缀
     */
    public static final String USER_PERMISSIONS_PREFIX = "user:permissions:";

    /**
     * 用户ID请求头
     */
    public static final String USER_ID_HEADER = "X-User-Id";

    /**
     * 租户ID请求头
     */
    public static final String TENANT_ID_HEADER = "X-Tenant-Id";

    /**
     * JWT令牌密钥
     */
    public static final String JWT_SECRET = "your-secret-key";

    /**
     * JWT令牌过期时间(毫秒)
     */
    public static final long JWT_EXPIRATION = 86400000;

    /**
     * 跳过认证的路径
     */
    public static final String[] SKIP_AUTH_PATHS = {
            ("/api/auth/login"),
            ("/api/auth/logout"),
            ("/api/auth/register"),
            ("/api/auth/refresh"),
            ("/api/public/**")
    };
}