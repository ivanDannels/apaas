package org.apaas.knowledge.infrastructure.client;

import org.apaas.api.inner.FileStorageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件存储客户端实现，通过apaas-inner-api调用apaas-system中的文件存储服务
 */
@Component
public class FileStorageClient {

    @Autowired
    private FileStorageApi fileStorageApi;

    /**
     * 上传文件
     */
    public Map<String, Object> uploadFile(MultipartFile file, String directory) {
        return fileStorageApi.uploadFile(file, directory);
    }

    /**
     * 删除文件
     */
    public Map<String, Object> deleteFile(String fileName, String directory) {
        return fileStorageApi.deleteFile(fileName, directory);
    }

    /**
     * 批量删除文件
     */
    public Map<String, Object> deleteAllFiles(String directory) {
        return fileStorageApi.deleteAllFiles(directory);
    }

    /**
     * 检查文件是否存在
     */
    public boolean fileExists(String fileName, String directory) {
        return fileStorageApi.fileExists(fileName, directory);
    }
}