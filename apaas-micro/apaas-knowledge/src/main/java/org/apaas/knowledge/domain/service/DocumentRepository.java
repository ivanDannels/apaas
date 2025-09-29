package org.apaas.knowledge.domain.service;

import org.apaas.knowledge.domain.model.Document;

import java.util.List;
import java.util.Optional;

/**
 * 文档存储库接口，定义对文档的基本操作
 */
public interface DocumentRepository {
    /**
     * 保存文档
     */
    Document save(Document document);

    /**
     * 根据文件名获取文档
     */
    Optional<Document> findByFileName(String fileName);

    /**
     * 获取所有文档
     */
    List<Document> findAll();

    /**
     * 删除文档
     */
    void deleteByFileName(String fileName);

    /**
     * 批量删除文档
     */
    void deleteAllByFileNames(List<String> fileNames);

    /**
     * 检查文件是否存在
     */
    boolean existsByFileName(String fileName);
}