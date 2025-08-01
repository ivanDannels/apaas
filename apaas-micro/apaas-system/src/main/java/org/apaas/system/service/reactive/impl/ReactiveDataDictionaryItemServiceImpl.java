package org.apaas.system.service.reactive.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apaas.system.domain.dto.DataDictionaryItemDTO;
import org.apaas.system.entity.DataDictionaryItem;
import org.apaas.system.repository.DataDictionaryItemRepository;
import org.apaas.system.service.reactive.ReactiveDataDictionaryItemService;
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
 * 响应式数据字典项服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveDataDictionaryItemServiceImpl implements ReactiveDataDictionaryItemService {

    private final DataDictionaryItemRepository dataDictionaryItemRepository;

    @Override
    public Flux<DataDictionaryItem> selectPage(Pageable pageable, DataDictionaryItemDTO query) {
        // 这里需要根据实际需求实现分页查询逻辑
        // 暂时返回所有数据字典项
        return dataDictionaryItemRepository.findAll();
    }

    @Override
    public Flux<DataDictionaryItem> selectByDictionaryId(Long dictionaryId) {
        return dataDictionaryItemRepository.findByDictionaryId(dictionaryId);
    }

    @Override
    public Mono<Boolean> create(DataDictionaryItem dataDictionaryItem) {
        return dataDictionaryItemRepository.save(dataDictionaryItem)
                .map(savedDataDictionaryItem -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> update(DataDictionaryItem dataDictionaryItem) {
        return dataDictionaryItemRepository.save(dataDictionaryItem)
                .map(updatedDataDictionaryItem -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return dataDictionaryItemRepository.deleteById(id)
                .then(Mono.just(true))
                .onErrorReturn(false);
    }

    @Override
    public Mono<Boolean> changeStatus(Long id, Integer status) {
        return dataDictionaryItemRepository.findById(id)
                .flatMap(dataDictionaryItem -> {
                    dataDictionaryItem.setStatus(status);
                    dataDictionaryItem.setUpdateTime(LocalDateTime.now());
                    return dataDictionaryItemRepository.save(dataDictionaryItem);
                })
                .map(updatedDataDictionaryItem -> true)
                .onErrorReturn(false);
    }

    @Override
    public Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryItemDTO query) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=dataDictionaryItem.xlsx");

        // 这里需要实现Excel导出逻辑
        // 暂时返回空响应
        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer dataBuffer = bufferFactory.wrap(new byte[0]);
        return response.writeWith(Mono.just(dataBuffer));
    }

    @Override
    public Mono<Boolean> importExcel(Long dictionaryId, byte[] fileData) {
        // 这里需要实现Excel导入逻辑
        // 暂时返回true
        return Mono.just(true);
    }
}