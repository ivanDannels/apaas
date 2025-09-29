/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.api.inner;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件存储服务接口，用于远程调用apaas-system中的文件存储服务
 * @author ivan
 */
@Component
@FeignClient(name = "apaas-system", path = "/api/system/file")
public interface FileStorageApi {
    
    /**
     * 上传文件
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Map<String, Object> uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("directory") String directory);
    
    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    Map<String, Object> deleteFile(@RequestParam("fileName") String fileName, @RequestParam("directory") String directory);
    
    /**
     * 批量删除文件
     */
    @DeleteMapping("/batch-delete")
    Map<String, Object> deleteAllFiles(@RequestParam("directory") String directory);
    
    /**
     * 检查文件是否存在
     */
    @GetMapping("/exists")
    boolean fileExists(@RequestParam("fileName") String fileName, @RequestParam("directory") String directory);
}