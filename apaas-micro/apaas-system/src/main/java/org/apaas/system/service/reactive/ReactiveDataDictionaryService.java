package org.apaas.system.service.reactive;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apaas.system.domain.dto.DataDictionaryDTO;
import org.apaas.system.entity.DataDictionary;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式数据字典服务接口
 */
@Service
public interface ReactiveDataDictionaryService {
    /**
     * 分页查询数据字典
     */
    Flux<DataDictionary> selectPage(Pageable pageable, DataDictionaryDTO query);

    /**
     * 创建数据字典
     */
    Mono<Boolean> create(DataDictionary dataDictionary);

    /**
     * 更新数据字典
     */
    Mono<Boolean> update(DataDictionary dataDictionary);

    /**
     * 删除数据字典
     */
    Mono<Boolean> delete(Long id);

    /**
     * 启用/停用数据字典
     */
    Mono<Boolean> changeStatus(Long id, Integer status);

    /**
     * 导出数据字典
     */
    Mono<Void> exportExcel(ServerWebExchange exchange, DataDictionaryDTO query);

    /**
     * 导入数据字典
     */
    Mono<Boolean> importExcel(byte[] fileData);
}