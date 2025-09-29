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