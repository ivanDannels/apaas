package org.apaas.system.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.system.domain.dto.SysConfigDTO;
import org.apaas.system.entity.SysConfig;
import org.apaas.system.repository.SysConfigRepository;
import org.apaas.system.service.reactive.ReactiveSysConfigService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 响应式参数配置服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveSysConfigServiceImpl implements ReactiveSysConfigService {

    private final SysConfigRepository configRepository;

    @Override
    public Flux<SysConfig> getConfigPage(Pageable pageable, SysConfigDTO query) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有配置
        return configRepository.findAll();
    }

    @Override
    public Mono<Boolean> addConfig(SysConfig config) {
        return configRepository.save(config)
                .map(savedConfig -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> updateConfig(SysConfig config) {
        return configRepository.save(config)
                .map(updatedConfig -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> deleteConfig(Long id) {
        return configRepository.deleteById(id)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> batchDeleteConfig(List<Long> ids) {
        return Flux.fromIterable(ids)
                .flatMap(id -> configRepository.deleteById(id))
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return configRepository.findById(id)
                .flatMap(config -> {
                    config.setStatus(status);
                    config.setUpdateTime(LocalDateTime.now());
                    return configRepository.save(config);
                })
                .map(updatedConfig -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<SysConfig> getConfigByCode(String code) {
        return configRepository.findByCode(code);
    }

    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, SysConfigDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=config.xlsx");

        // 这里需要实现Excel导出逻辑
        // 暂时返回空响应
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(new byte[0]);
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public Mono<Boolean> importExcel(byte[] fileData) {
        // 这里需要实现Excel导入逻辑
        // 暂时返回true
        return Mono.just(true);
    }
}