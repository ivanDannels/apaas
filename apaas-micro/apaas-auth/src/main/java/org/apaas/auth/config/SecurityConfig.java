package org.apaas.auth.config;

import lombok.RequiredArgsConstructor;
import org.apaas.auth.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 安全配置
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 安全过滤器链
     */
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 禁用HTTP Basic认证
                .httpBasic(AbstractHttpConfigurer::disable)
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                // 禁用默认登出
                .logout(AbstractHttpConfigurer::disable)
                // 禁用匿名用户
                .anonymous(AbstractHttpConfigurer::disable)
                // 禁用Session
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 授权请求
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        // 放行OPTIONS请求
                        .antMatchers("OPTIONS", "/**").permitAll()
                        // 放行Swagger相关资源
                        .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // 放行健康检查
                        .antMatchers("/actuator/**").permitAll()
                        // 放行登录接口
                        .antMatchers("/login", "/oauth2/**").permitAll()
                        // 其他请求需要认证
                        .anyRequest().authenticated())
                // 用户详情服务
                .userDetailsService(userDetailsService);

        return http.build();
    }
}