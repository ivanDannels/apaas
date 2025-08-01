package org.apaas.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * 国际化服务类
 */
@Service
public class InternationalizationService {

    @Autowired
    private MessageSource messageSource;

    /**
     * 根据消息键获取当前语言的消息
     *
     * @param code 消息键
     * @return 消息内容
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * 根据消息键和参数获取当前语言的消息
     *
     * @param code 消息键
     * @param args 参数数组
     * @return 消息内容
     */
    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * 根据消息键、参数和语言获取消息
     *
     * @param code 消息键
     * @param args 参数数组
     * @param locale 语言
     * @return 消息内容
     */
    public String getMessage(String code, Object[] args, Locale locale) {
        return messageSource.getMessage(code, args, code, locale);
    }

    /**
     * 设置当前语言
     *
     * @param language 语言代码，如zh_CN、en等
     */
    public void setLanguage(String language) {
        Locale locale;
        if (language.contains("_")). {
            String[] parts = language.split("_");
            locale = new Locale(parts[0], parts[1]);
        } else {
            locale = new Locale(language);
        }
        LocaleContextHolder.setLocale(locale);
    }

    /**
     * 获取当前语言
     *
     * @return 当前语言代码
     */
    public String getCurrentLanguage() {
        Locale locale = LocaleContextHolder.getLocale();
        return locale.toString();
    }
}