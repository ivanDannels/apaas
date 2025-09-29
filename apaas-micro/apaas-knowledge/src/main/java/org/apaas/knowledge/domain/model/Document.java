package org.apaas.knowledge.domain.model;

import java.util.Objects;

/**
 * 文档模型，表示知识库中的单个文档
 */
public class Document {
    private String id;
    private String content;
    private String fileName;
    private long fileSize;
    private long lastModifiedTime;
    private boolean selected;

    public Document() {
    }

    public Document(String content, String fileName, long fileSize, long lastModifiedTime) {
        this.content = content;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.lastModifiedTime = lastModifiedTime;
        this.selected = false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(fileName, document.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}