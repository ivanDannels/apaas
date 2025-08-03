package org.apaas.system.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.system.entity.Files;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

/**
 * 响应式文件服务接口
 * @author ivan
 */
public interface ReactiveFileService extends BaseService<Files,  Long> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问URL
     */
    Mono<String> uploadFile(FilePart file);

    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 文件名
     * @return 文件访问URL
     */
    Mono<String> uploadFile(FilePart file, String fileName);

    /**
     * 上传文件
     *
     * @param dataBuffer 数据缓冲区
     * @param fileName   文件名
     * @return 文件访问URL
     */
    Mono<String> uploadFile(DataBuffer dataBuffer, String fileName);

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return 是否成功
     */
    Mono<Boolean> deleteFile(String fileUrl);

    /**
     * 获取文件
     *
     * @param fileUrl 文件URL
     * @return 文件数据缓冲区
     */
    Mono<DataBuffer> getFile(String fileUrl);

    /**
     * 获取文件访问URL
     *
     * @param fileName 文件名
     * @return 文件访问URL
     */
    Mono<String> getFileUrl(String fileName);

    Mono<Files> getFilePath(String filePath);
}