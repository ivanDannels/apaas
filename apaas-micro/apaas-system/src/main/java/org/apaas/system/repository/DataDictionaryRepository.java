package org.apaas.system.repository;

import org.apaas.system.entity.DataDictionary;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * 数据字典Repository接口
 */
@Repository
public interface DataDictionaryRepository extends R2dbcRepository<DataDictionary, Long> {
}