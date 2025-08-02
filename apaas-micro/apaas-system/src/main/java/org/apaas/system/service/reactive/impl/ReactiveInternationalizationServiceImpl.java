package org.apaas.system.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.system.service.reactive.ReactiveInternationalizationService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 响应式国际化服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveInternationalizationServiceImpl implements ReactiveInternationalizationService {
    
    private static final Logger log = LoggerFactory.getLogger(ReactiveInternationalizationServiceImpl.class);

    private final MessageSource messageSource;

    @Override
    public Mono<String> getMessage(String code) {
        return getMessage(code, null);
    }

    @Override
    public Mono<String> getMessage(String code, Object[] args) {
        return getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @Override
    public Mono<String> getMessage(String code, Object[] args, Locale locale) {
        try {
            String message = messageSource.getMessage(code, args, code, locale);
            return Mono.just(message);
        } catch (Exception e) {
            log.error("获取国际化消息失败", e);
            return Mono.just(code);
        }
    }

    @Override
    public Mono<Void> setLanguage(String language) {
        try {
            Locale locale;
            if (language.contains("_")) {
                String[] parts = language.split("_");
                locale = new Locale(parts[0], parts[1]);
            } else {
                locale = new Locale(language);
            }
            LocaleContextHolder.setLocale(locale);
            return Mono.empty();
        } catch (Exception e) {
            log.error("设置语言失败", e);
            return Mono.error(e);
        }
    }

    @Override
    public Mono<String> getCurrentLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        return Mono.just(locale.toString());
    }
}