package org.apaas.system.service.reactive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionary;
import org.apaas.system.repository.DataDictionaryRepository;
import org.apaas.system.service.reactive.ReactiveDataDictionaryService;
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
 * 响应式数据字典服务实现
 * @author ivan
 */
@Slf4j
@Service
public class ReactiveDataDictionaryServiceImpl extends BaseServiceImpl<DataDictionary, Long, DataDictionaryRepository> implements ReactiveDataDictionaryService {


    public ReactiveDataDictionaryServiceImpl(DataDictionaryRepository repository, RedisDomainEventPublisher<EntityChangedEvent<DataDictionary>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Flux<DataDictionary> selectPage(Pageable pageable, DataDictionaryDTO query) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有数据字典
        return repository.findAll();
    }

    @Override
    public Mono<Boolean> create(DataDictionary dataDictionary) {
        return repository.save(dataDictionary)
                .map(savedDataDictionary -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> update(DataDictionary dataDictionary) {
        return repository.save(dataDictionary)
                .map(updatedDataDictionary -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return repository.deleteById(id)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return repository.findById(id)
                .flatMap(dataDictionary -> {
                    dataDictionary.setStatus(status);
                    dataDictionary.setUpdatedTime(LocalDateTime.now());
                    return repository.save(dataDictionary);
                })
                .map(updatedDataDictionary -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dataDictionary.xlsx");

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