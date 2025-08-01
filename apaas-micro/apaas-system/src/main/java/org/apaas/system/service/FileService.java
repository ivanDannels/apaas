package org.apaas.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件服务接口
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file);

    /**
     * 上传文件
     *
     * @param file     文件
     * @param fileName 文件名
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String fileName);

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param fileName    文件名
     * @return 文件访问URL
     */
    String uploadFile(InputStream inputStream, String fileName);

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return 是否成功
     */
    boolean deleteFile(String fileUrl);

    /**
     * 获取文件
     *
     * @param fileUrl 文件URL
     * @return 文件流
     */
    InputStream getFile(String fileUrl);

    /**
     * 获取文件访问URL
     *
     * @param fileName 文件名
     * @return 文件访问URL
     */
    String getFileUrl(String fileName);
}