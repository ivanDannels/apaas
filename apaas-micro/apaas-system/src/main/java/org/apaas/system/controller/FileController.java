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
package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.rest.ReactiveBaseController;
import org.apaas.system.entity.Files;
import org.apaas.system.service.reactive.ReactiveFileService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件控制器 - 响应式实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/reactive/files")
@Tag(name = "文件管理", description = "文件上传下载接口")
public class FileController extends ReactiveBaseController<Files, Long, ReactiveFileService> {
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    public FileController(ReactiveFileService service) {
        super(service);
    }
    
    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件URL
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "上传文件", description = "上传文件并返回访问URL")
    public Mono<String> upload(@Parameter(description = "文件", required = true) @RequestPart("file") FilePart file) {
        if (Objects.isNull(file)) {
            return Mono.error(new IllegalArgumentException("上传文件不能为空"));
        }
        return service.uploadFile(file);
    }
    
    /**
     * 下载文件
     *
     * @param fileUrl 文件URL
     * @return 文件流
     */
    @GetMapping("/download")
    @Operation(summary = "下载文件", description = "根据文件URL下载文件")
    public Mono<Void> download(@Parameter(description = "文件URL", required = true) @RequestParam("fileUrl") String fileUrl, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        
        return service.getFilePath(fileUrl).flatMap(file -> {
            try {
                Path path = Path.of(fileUrl);
                // 获取文件名
                String fileName = file.getFileName().toString();
                // 设置响应头
                response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
                response.getHeaders().setContentDispositionFormData("attachment", fileName);
                
                // 异步读取文件
                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
                
                DataBufferFactory bufferFactory = response.bufferFactory();
                long fileSize = java.nio.file.Files.size(path);
                ByteBuffer buffer = ByteBuffer.allocate(8192);
                
                return readFile(fileChannel, buffer, bufferFactory, response, 0, fileSize);
            } catch (IOException e) {
                log.error("文件下载失败", e);
                response.setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
                return response.setComplete();
            }
        }).onErrorResume(e -> {
            log.error("文件下载失败", e);
            response.setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
            return response.setComplete();
        });
    }
    
    /**
     * 异步读取文件并写入响应
     */
    private Mono<Void> readFile(AsynchronousFileChannel fileChannel, ByteBuffer buffer, DataBufferFactory bufferFactory, ServerHttpResponse response, long position, long fileSize) {
        if (position >= fileSize) {
            return Mono.empty();
        }
        
        return Mono.create(sink -> {
            fileChannel.read(buffer, position, buffer, new java.nio.channels.CompletionHandler<Integer, ByteBuffer>() {
                
                @Override
                public void completed(Integer bytesRead, ByteBuffer attachment) {
                    if (bytesRead == -1) {
                        sink.success();
                        return;
                    }
                    
                    attachment.flip();
                    DataBuffer dataBuffer = bufferFactory.wrap(attachment);
                    response.writeWith(Mono.just(dataBuffer)).then(Mono.defer(() -> {
                        attachment.clear();
                        return readFile(fileChannel, attachment, bufferFactory, response, position + bytesRead, fileSize);
                    })).subscribe(sink::success, sink::error);
                }
                
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    sink.error(exc);
                }
            });
        });
    }
    
    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除文件", description = "根据文件URL删除文件")
    public Mono<Boolean> delete(@Parameter(description = "文件URL", required = true) @RequestParam("fileUrl") String fileUrl) {
        return service.deleteFile(fileUrl).onErrorReturn(false);
    }
    
    /**
     * 获取文件
     *
     * @param fileName 文件名
     * @return 文件流
     */
    @GetMapping("/files/{year}/{month}/{day}/{fileName}")
    @Operation(summary = "获取文件", description = "根据文件路径获取文件")
    public Mono<Void> getFile(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("day") String day, @PathVariable("fileName") String fileName, ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        String filePath = year + "/" + month + "/" + day + "/" + fileName;
        
        return service.getFilePath(filePath).flatMap(file -> {
            try {
                Path path = Path.of(file.getFilePath());
                // 获取文件类型
                MediaType mediaType = getMediaType(fileName);
                // 设置响应头
                response.getHeaders().setContentType(mediaType);
                
                // 异步读取文件
                AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
                
                DataBufferFactory bufferFactory = response.bufferFactory();
                long fileSize = java.nio.file.Files.size(path);
                ByteBuffer buffer = ByteBuffer.allocate(8192);
                
                return readFile(fileChannel, buffer, bufferFactory, response, 0, fileSize);
            } catch (IOException e) {
                log.error("获取文件失败", e);
                response.setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
                return response.setComplete();
            }
        }).onErrorResume(e -> {
            log.error("获取文件失败");
            response.setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
            return response.setComplete();
        });
    }
    
    /**
     * 获取文件类型
     *
     * @param fileName 文件名
     * @return 文件类型
     */
    private MediaType getMediaType(String fileName) {
        // 文件类型映射
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (fileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (fileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (fileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (fileName.endsWith(".html")) {
            return MediaType.TEXT_HTML;
        } else if (fileName.endsWith(".txt")) {
            return MediaType.TEXT_PLAIN;
        } else if (fileName.endsWith(".xml")) {
            return MediaType.APPLICATION_XML;
        } else if (fileName.endsWith(".json")) {
            return MediaType.APPLICATION_JSON;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}