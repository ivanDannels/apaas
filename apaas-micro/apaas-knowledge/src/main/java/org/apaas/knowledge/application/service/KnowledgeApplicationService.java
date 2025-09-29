package org.apaas.knowledge.application.service;

import org.apaas.knowledge.domain.model.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 知识库应用服务接口，定义知识库的核心业务操作
 */
public interface KnowledgeApplicationService {
    /**
     * 上传文件到知识库
     */
    Map<String, Object> uploadFile(MultipartFile file);

    /**
     * 获取所有已上传的文件
     */
    List<Document> getAllFiles();

    /**
     * 删除指定文件
     */
    Map<String, Object> deleteFile(String fileName);

    /**
     * 批量删除文件
     */
    Map<String, Object> batchDeleteFiles(List<String> fileNames);

    /**
     * 清空知识库
     */
    Map<String, Object> clearKnowledgeBase();

    /**
     * 选择文件用于问答
     */
    Map<String, Object> selectFiles(List<String> fileNames);

    /**
     * 排除单个文件
     */
    Map<String, Object> excludeFile(String fileName);

    /**
     * 与知识库交互进行问答
     */
    String chatWithKnowledge(String query);

    /**
     * 流式与知识库交互进行问答
     */
    void chatWithKnowledgeStream(String query, Consumer<String> callback);
}