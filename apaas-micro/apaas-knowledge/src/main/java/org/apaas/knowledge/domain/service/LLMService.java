package org.apaas.knowledge.domain.service;

import java.util.function.Consumer;

/**
 * 语言模型服务接口，定义与语言模型交互的操作
 */
public interface LLMService {
    /**
     * 生成文本响应
     */
    String generateText(String prompt);

    /**
     * 流式生成文本响应
     */
    void generateTextStream(String prompt, Consumer<String> callback);
}