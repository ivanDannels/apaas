//package org.apaas.core.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
///**
// * Spring Security配置类
// */
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    /**
//     * 密码编码器
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        return new MapReactiveUserDetailsService(user, admin);
//    }
//
//    @Bean
//    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
//        return Mono::just;
//    }
//
//    /**
//     * 安全过滤器链
//     */
//    @Bean
//    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
//        http
//                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers("/public/**").permitAll()   // 开放公共路径
//                        .pathMatchers("/admin/**").hasRole("ADMIN") // 管理员权限
//                        .anyExchange().authenticated()            // 其他路径需认证
//                )
//                .formLogin(withDefaults())                   // 启用默认表单登录
//                .csrf(ServerHttpSecurity.CsrfSpec::disable); // 禁用CSRF（API场景推荐）
//        return http.build();
//    }
//}