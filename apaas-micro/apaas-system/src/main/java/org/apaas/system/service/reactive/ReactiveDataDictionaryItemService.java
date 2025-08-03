package org.apaas.system.service.reactive;

import org.apaas.system.domain.dto.DataDictionaryItemDTO;
import org.apaas.system.entity.DataDictionaryItem;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 响应式数据字典项服务接口
 * @author ivan
 */
@Service
public interface ReactiveDataDictionaryItemService {
    /**
     * 分页查询数据字典项
     */
    Flux<DataDictionaryItem> selectPage(Pageable pageable, DataDictionaryItemDTO query);

    /**
     * 根据字典ID查询字典项列表
     */
    Flux<DataDictionaryItem> selectByDictionaryId(Long dictionaryId);

    /**
     * 创建数据字典项
     */
    Mono<Boolean> create(DataDictionaryItem dataDictionaryItem);

    /**
     * 更新数据字典项
     */
    Mono<Boolean> update(DataDictionaryItem dataDictionaryItem);

    /**
     * 删除数据字典项
     */
    Mono<Boolean> delete(Long id);

    /**
     * 启用/停用数据字典项
     */
    Mono<Boolean> changeStatus(Long id, Integer status);

    /**
     * 导出数据字典项
     */
    Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryItemDTO query);

    /**
     * 导入数据字典项
     */
    Mono<Boolean> importExcel(Long dictionaryId, byte[] fileData);
}