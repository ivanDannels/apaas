package org.apaas.knowledge.domain.service;

import org.apaas.knowledge.domain.model.Document;

import java.util.List;

/**
 * 向量存储库接口，定义向量数据库相关操作
 */
public interface VectorStoreRepository {
    /**
     * 创建向量存储
     */
    void createVectorStore(List<Document> documents);

    /**
     * 搜索相似文档
     */
    List<Document> searchSimilar(String query, int k);

    /**
     * 检查向量存储是否存在
     */
    boolean hasVectorStore();

    /**
     * 清空向量存储
     */
    void clearVectorStore();
}