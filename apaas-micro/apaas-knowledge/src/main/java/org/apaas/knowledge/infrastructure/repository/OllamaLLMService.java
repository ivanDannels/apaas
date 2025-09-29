package org.apaas.knowledge.infrastructure.repository;

import org.apaas.knowledge.domain.service.LLMService;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 语言模型服务的Ollama实现
 */
@Service
public class OllamaLLMService implements LLMService {

    @Autowired
    private ChatClient chatClient;

    @Override
    public String generateText(String prompt) {
        // 创建提示模板
        PromptTemplate promptTemplate = new PromptTemplate("{prompt}");
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("prompt", prompt);
        
        // 生成提示
        Prompt finalPrompt = promptTemplate.create(promptParameters);
        
        // 调用ChatClient生成文本响应
        return chatClient.call(finalPrompt).getResult().getOutput().getContent();
    }

    @Override
    public void generateTextStream(String prompt, Consumer<String> callback) {
        // 创建提示模板
        PromptTemplate promptTemplate = new PromptTemplate("{prompt}");
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("prompt", prompt);
        
        // 生成提示
        Prompt finalPrompt = promptTemplate.create(promptParameters);
        
        // 调用ChatClient流式生成文本响应
        chatClient.stream(finalPrompt).forEach(chatResponse -> {
            String content = chatResponse.getResult().getOutput().getContent();
            if (content != null && !content.isEmpty()) {
                callback.accept(content);
            }
        });
    }
}