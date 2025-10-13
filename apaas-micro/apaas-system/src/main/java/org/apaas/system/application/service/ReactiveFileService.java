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
package org.apaas.system.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.system.domain.model.Files;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

/**
 * 响应式文件服务接口
 * @author ivan
 */
public interface ReactiveFileService extends ApplicationService<Files, Long> {
    
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