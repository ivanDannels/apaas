package org.apaas.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证配置属性
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    /**
     * 令牌有效期（秒）
     */
    private Integer tokenValiditySeconds = 1800;

    /**
     * 刷新令牌有效期（秒）
     */
    private Integer refreshTokenValiditySeconds = 604800;

    /**
     * 客户端配置
     */
    private List<Client> clients = new ArrayList<>();

    /**
     * 客户端配置
     */
    @Data
    public static class Client {

        /**
         * 客户端ID
         */
        private String clientId;

        /**
         * 客户端密钥
         */
        private String clientSecret;

        /**
         * 重定向URI
         */
        private String redirectUris;

        /**
         * 授权范围
         */
        private String scopes;
    }
}