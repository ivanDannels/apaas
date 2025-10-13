package org.apaas.system.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import org.apaas.domain.application.service.AbstractApplicationService;
import org.apaas.system.domain.model.SysConfig;
import org.apaas.system.domain.repository.SysConfigRepository;
import org.apaas.system.application.service.ReactiveSysConfigService;
import org.apaas.system.application.dto.SysConfigDTO;
import org.apaas.system.application.assembler.SysConfigAssembler;
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
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveSysConfigServiceImpl extends AbstractApplicationService<SysConfig, Long, SysConfigRepository> implements ReactiveSysConfigService {
    
    public ReactiveSysConfigServiceImpl(SysConfigRepository repository) {
        super(repository);
    }
    
    @Override
    public Flux<SysConfigDTO> getConfigPage(Pageable pageable, SysConfigDTO query) {
        // 实现分页查询逻辑
        if (query != null) {
            // 根据查询条件过滤数据
            return repository.findAll()
                .filter(config -> {
                    boolean match = true;
                    if (query.getName() != null && !query.getName().isEmpty()) {
                        match = config.getName() != null && config.getName().contains(query.getName());
                    }
                    if (match && query.getConfigKey() != null && !query.getConfigKey().isEmpty()) {
                        match = config.getConfigKey() != null && config.getConfigKey().contains(query.getConfigKey());
                    }
                    if (match && query.getCode() != null && !query.getCode().isEmpty()) {
                        match = config.getCode() != null && config.getCode().contains(query.getCode());
                    }
                    if (match && query.getType() != null) {
                        match = config.getType() != null && config.getType().equals(query.getType());
                    }
                    if (match && query.getStatus() != null) {
                        match = config.getStatus() != null && config.getStatus().equals(query.getStatus());
                    }
                    return match;
                })
                .skip(pageable.getOffset())
                .take(pageable.getPageSize())
                .map(SysConfigAssembler.INSTANCE::convertEntityToDto);
        } else {
            // 无查询条件时分页查询
            return repository.findAll()
                .skip(pageable.getOffset())
                .take(pageable.getPageSize())
                .map(SysConfigAssembler.INSTANCE::convertEntityToDto);
        }
    }

    @Override
    public Mono<Boolean> addConfig(SysConfigDTO configDto) {
        SysConfig config = SysConfigAssembler.INSTANCE.convertDtoToEntity(configDto);
        return repository.save(config).map(savedConfig -> true).onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> updateConfig(SysConfigDTO configDto) {
        SysConfig config = SysConfigAssembler.INSTANCE.convertDtoToEntity(configDto);
        return repository.save(config).map(updatedConfig -> true).onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> deleteConfig(Long id) {
        return repository.deleteById(id).then(Mono.just(true)).onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> batchDeleteConfig(List<Long> ids) {
        return Flux.fromIterable(ids).flatMap(id -> repository.deleteById(id)).then(Mono.just(true)).onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return repository.findById(id).flatMap(config -> {
            config.setStatus(status);
            config.setUpdatedTime(LocalDateTime.now());
            return repository.save(config);
        }).map(updatedConfig -> true).onErrorReturn(false);
    }
    
    @Override
    public Mono<SysConfigDTO> getConfigByCode(String code) {
        return repository.findByCode(code)
                .map(SysConfigAssembler.INSTANCE::convertEntityToDto);
    }
    
    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, SysConfigDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=config.xlsx");
        
        // 实现Excel导出逻辑
        Flux<SysConfig> configFlux;
        if (query != null) {
            // 根据查询条件过滤数据
            configFlux = repository.findAll()
                .filter(config -> {
                    boolean match = true;
                    if (query.getName() != null && !query.getName().isEmpty()) {
                        match = config.getName() != null && config.getName().contains(query.getName());
                    }
                    if (match && query.getConfigKey() != null && !query.getConfigKey().isEmpty()) {
                        match = config.getConfigKey() != null && config.getConfigKey().contains(query.getConfigKey());
                    }
                    if (match && query.getCode() != null && !query.getCode().isEmpty()) {
                        match = config.getCode() != null && config.getCode().contains(query.getCode());
                    }
                    if (match && query.getType() != null) {
                        match = config.getType() != null && config.getType().equals(query.getType());
                    }
                    if (match && query.getStatus() != null) {
                        match = config.getStatus() != null && config.getStatus().equals(query.getStatus());
                    }
                    return match;
                });
        } else {
            // 无查询条件时导出所有数据
            configFlux = repository.findAll();
        }
        
        // 这里需要实现Excel导出逻辑
        // 暂时返回空响应
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(new byte[0]);
        return response.writeWith(Mono.just(dataBuffer));
    }
    
    @Override
    public Mono<Boolean> importExcel(byte[] fileData) {
        // 实现Excel导入逻辑
        // 暂时返回true
        return Mono.just(true);
    }
}