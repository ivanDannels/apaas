package org.apaas.api.inner;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件存储服务接口，用于远程调用apaas-system中的文件存储服务
 */
@FeignClient(name = "apaas-system", path = "/api/system/file")
public interface FileStorageApi {

    /**
     * 上传文件
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, Object> uploadFile(@RequestPart("file") MultipartFile file, 
                                  @RequestParam("directory") String directory);

    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    Map<String, Object> deleteFile(@RequestParam("fileName") String fileName, 
                                  @RequestParam("directory") String directory);

    /**
     * 批量删除文件
     */
    @DeleteMapping("/batch-delete")
    Map<String, Object> deleteAllFiles(@RequestParam("directory") String directory);

    /**
     * 检查文件是否存在
     */
    @GetMapping("/exists")
    boolean fileExists(@RequestParam("fileName") String fileName, 
                      @RequestParam("directory") String directory);
}