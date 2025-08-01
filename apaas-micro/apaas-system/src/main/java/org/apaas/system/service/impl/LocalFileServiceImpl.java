package org.apaas.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apaas.core.exception.BusinessException;
import org.apaas.system.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 本地文件服务实现
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "system.uploadType", havingValue = "local", matchIfMissing = true)
public class LocalFileServiceImpl implements FileService {

    @Value("${system.uploadPath:/tmp/upload}")
    private String uploadPath;

    @Value("${server.servlet.context-path:/system}")
    private String contextPath;

    @Override
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        return uploadFile(file, generateFileName(originalFilename));
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        try {
            return uploadFile(file.getInputStream(), fileName);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        try {
            // 创建目录
            Path directory = Paths.get(uploadPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // 创建文件
            Path filePath = Paths.get(uploadPath, fileName);
            // 确保父目录存在
            Files.createDirectories(filePath.getParent());

            // 写入文件
            try (OutputStream outputStream = Files.newOutputStream(filePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return getFileUrl(fileName);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.indexOf(contextPath) + contextPath.length() + 6); // 去掉/files/
            Path filePath = Paths.get(uploadPath, fileName);
            Files.deleteIfExists(filePath);
            return true;
        } catch (IOException e) {
            log.error("文件删除失败", e);
            return false;
        }
    }

    @Override
    public InputStream getFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.indexOf(contextPath) + contextPath.length() + 6); // 去掉/files/
            Path filePath = Paths.get(uploadPath, fileName);
            return Files.newInputStream(filePath);
        } catch (IOException e) {
            log.error("获取文件失败", e);
            throw new BusinessException("获取文件失败");
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        // 返回文件访问URL
        return contextPath + "/files/" + fileName;
    }

    /**
     * 生成文件名
     *
     * @param originalFilename 原始文件名
     * @return 生成的文件名
     */
    private String generateFileName(String originalFilename) {
        // 获取文件后缀
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        // 生成文件名：日期路径 + UUID + 后缀
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) + "/" +
                UUID.randomUUID().toString().replaceAll("-", "") + suffix;
    }
}