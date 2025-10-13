/*
 * Copyright (c) 2012-2025, ivan (ivan.dannels@gmail.com).
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apaas.system.domain.repository;

import org.apaas.domain.domain.repository.ReactiveBaseRepository;
import org.apaas.system.domain.model.DataDictionaryItem;
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