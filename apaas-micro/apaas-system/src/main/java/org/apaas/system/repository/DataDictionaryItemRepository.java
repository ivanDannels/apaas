package org.apaas.system.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.system.entity.DataDictionaryItem;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * 数据字典项Repository接口
 * @author ivan
 */
@Repository
public interface DataDictionaryItemRepository extends ReactiveBaseRepository<DataDictionaryItem, Long> {
    /**
     * 根据字典ID查询字典项列表
     *
     * @param dictionaryId 字典ID
     * @return 字典项列表
     */
    Flux<DataDictionaryItem> findByDictionaryId(Long dictionaryId);
}