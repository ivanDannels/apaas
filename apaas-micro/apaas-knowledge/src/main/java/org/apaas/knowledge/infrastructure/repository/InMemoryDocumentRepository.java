package org.apaas.knowledge.infrastructure.repository;

import org.apaas.knowledge.domain.model.Document;
import org.apaas.knowledge.domain.service.DocumentRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文档存储库的内存实现
 */
@Repository
public class InMemoryDocumentRepository implements DocumentRepository {

    // 使用ConcurrentHashMap存储文档，确保线程安全
    private final Map<String, Document> documentStore = new ConcurrentHashMap<>();

    @Override
    public Document save(Document document) {
        if (document.getId() == null) {
            document.setId(UUID.randomUUID().toString());
        }
        documentStore.put(document.getFileName(), document);
        return document;
    }

    @Override
    public Optional<Document> findByFileName(String fileName) {
        return Optional.ofNullable(documentStore.get(fileName));
    }

    @Override
    public List<Document> findAll() {
        List<Document> documents = new ArrayList<>(documentStore.values());
        // 按修改时间排序，最新的在前
        documents.sort((d1, d2) -> Long.compare(d2.getLastModifiedTime(), d1.getLastModifiedTime()));
        return documents;
    }

    @Override
    public void deleteByFileName(String fileName) {
        documentStore.remove(fileName);
    }

    @Override
    public void deleteAllByFileNames(List<String> fileNames) {
        for (String fileName : fileNames) {
            documentStore.remove(fileName);
        }
    }

    @Override
    public boolean existsByFileName(String fileName) {
        return documentStore.containsKey(fileName);
    }
}