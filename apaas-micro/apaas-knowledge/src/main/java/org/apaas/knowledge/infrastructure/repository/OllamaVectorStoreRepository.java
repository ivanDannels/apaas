package org.apaas.knowledge.infrastructure.repository;

import org.apaas.knowledge.domain.model.Document;
import org.apaas.knowledge.domain.service.VectorStoreRepository;
import org.springframework.ai.document.Document as AiDocument;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 向量存储库的Ollama实现
 */
@Repository
public class OllamaVectorStoreRepository implements VectorStoreRepository {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private EmbeddingClient embeddingClient;

    private boolean vectorStoreCreated = false;

    @Override
    public void createVectorStore(List<Document> documents) {
        // 将自定义Document转换为Spring AI的Document
        List<AiDocument> aiDocuments = documents.stream()
                .map(doc -> new AiDocument(doc.getContent(), Map.of("fileName", doc.getFileName())))
                .collect(Collectors.toList());
        
        // 清空现有向量存储并添加新文档
        vectorStore.deleteAll();
        vectorStore.add(aiDocuments);
        
        vectorStoreCreated = true;
    }

    @Override
    public List<Document> searchSimilar(String query, int k) {
        // 搜索相似文档
        List<AiDocument> similarDocs = vectorStore.similaritySearch(query, k);
        
        // 将Spring AI的Document转换回自定义Document
        return similarDocs.stream()
                .map(aiDoc -> {
                    Document doc = new Document();
                    doc.setContent(aiDoc.getContent());
                    doc.setFileName((String) aiDoc.getMetadata().get("fileName"));
                    return doc;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasVectorStore() {
        return vectorStoreCreated;
    }

    @Override
    public void clearVectorStore() {
        vectorStore.deleteAll();
        vectorStoreCreated = false;
    }
}