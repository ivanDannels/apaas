package org.apaas.system.application.service;

import org.apaas.domain.application.service.ApplicationService;
import org.apaas.system.domain.model.Internationalization;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * 响应式国际化服务接口
 * @author ivan
 */
public interface ReactiveInternationalizationService extends ApplicationService<Internationalization, Long> {
    
    /**
     * 根据消息键获取当前语言的消息
     *
     * @param code 消息键
     * @return 消息内容
     */
    Mono<String> getMessage(String code);
    
    /**
     * 根据消息键和参数获取当前语言的消息
     *
     * @param code 消息键
     * @param args 参数数组
     * @return 消息内容
     */
    Mono<String> getMessage(String code, Object[] args);
    
    /**
     * 根据消息键、参数和语言获取消息
     *
     * @param code   消息键
     * @param args   参数数组
     * @param locale 语言
     * @return 消息内容
     */
    Mono<String> getMessage(String code, Object[] args, Locale locale);
    
    /**
     * 设置当前语言
     *
     * @param language 语言代码，如zh_CN、en等
     */
    Mono<Void> setLanguage(String language);
    
    /**
     * 获取当前语言
     *
     * @return 当前语言代码
     */
    Mono<String> getCurrentLanguage();
    
    /**
     * 根据语言获取所有消息
     *
     * @param language 语言代码，如zh_CN、en等
     * @return 消息列表
     */
    Flux<Internationalization> getMessagesByLanguage(String language);
}