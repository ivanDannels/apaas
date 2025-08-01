package org.apaas.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.web.domain.AjaxResult;
import org.apaas.system.service.FileService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件控制器
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "文件管理", description = "文件上传下载接口")
public class FileController {

    private final FileService fileService;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件URL
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传文件", description = "上传文件并返回访问URL")
    public Mono<AjaxResult<String>> upload(
            @Parameter(description = "文件", required = true)
            @RequestPart("file") FilePart file) {
        if (file.isEmpty()) {
            return Mono.just(AjaxResult.error("上传文件不能为空"));
        }
        String url = fileService.uploadFile(file);
        return Mono.just(AjaxResult.success("上传成功", url));
    }

    /**
     * 下载文件
     *
     * @param fileUrl 文件URL
     * @return 文件流
     */
    @GetMapping("/download")
    @Operation(summary = "下载文件", description = "根据文件URL下载文件")
    public Mono<Void> download(
            @Parameter(description = "文件URL", required = true)
            @RequestParam("fileUrl") String fileUrl,
            ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        try (InputStream inputStream = fileService.getFile(fileUrl)) {
            // 获取文件名
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            // 设置响应头
            response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
            response.getHeaders().setContentDispositionFormData("attachment", fileName);
            // 读取文件内容
            byte[] bytes = inputStream.readAllBytes();
            DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap(bytes);
            // 返回文件
            return response.writeWith(Mono.just(dataBuffer));
        } catch (IOException e) {
            log.error("文件下载失败", e);
            response.setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
            return response.setComplete();
        }
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件URL
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除文件", description = "根据文件URL删除文件")
    public Mono<AjaxResult<Boolean>> delete(
            @Parameter(description = "文件URL", required = true)
            @RequestParam("fileUrl") String fileUrl) {
        boolean result = fileService.deleteFile(fileUrl);
        return Mono.just(result ? AjaxResult.success("删除成功", true) : AjaxResult.error("删除失败"));
    }

    /**
     * 获取文件
     *
     * @param fileName 文件名
     * @return 文件流
     */
    @GetMapping("/files/{year}/{month}/{day}/{fileName}")
    @Operation(summary = "获取文件", description = "根据文件路径获取文件")
    public Mono<Void> getFile(
            @PathVariable("year") String year,
            @PathVariable("month") String month,
            @PathVariable("day") String day,
            @PathVariable("fileName") String fileName,
            ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        try (InputStream inputStream = fileService.getFile(year + "/" + month + "/" + day + "/" + fileName)) {
            // 获取文件类型
            MediaType mediaType = getMediaType(fileName);
            // 设置响应头
            response.getHeaders().setContentType(mediaType);
            // 读取文件内容
            byte[] bytes = inputStream.readAllBytes();
            DataBufferFactory bufferFactory = new DefaultDataBufferFactory();
            DataBuffer dataBuffer = bufferFactory.wrap(bytes);
            // 返回文件
            return response.writeWith(Mono.just(dataBuffer));
        } catch (IOException e) {
            log.error("获取文件失败", e);
            response.setStatusCode(org.springframework.http.HttpStatus.NOT_FOUND);
            return response.setComplete();
        }
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