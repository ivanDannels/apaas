package org.apaas.system.repository;

import org.apaas.system.entity.DataDictionary;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据字典Repository接口
 */
@Repository
public interface DataDictionaryRepository extends ReactiveBaseRepository<DataDictionary, Long> {
}