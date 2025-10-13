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
package org.apaas.design.platform.infrastructure.repository;

import org.apaas.design.platform.domain.model.APIEntity;
import org.apaas.design.platform.domain.repository.APIEntityRepository;
import org.apaas.design.platform.application.service.ReactiveAPIEntityService;
import org.apaas.application.service.AbstractApplicationService;
import org.springframework.stereotype.Service;

/**
 * 响应式API实体服务实现类
 * @author ivan
 */
@Service
public class ReactiveAPIEntityServiceImpl extends AbstractApplicationService<APIEntity, Long, APIEntityRepository> implements ReactiveAPIEntityService {
    
    public ReactiveAPIEntityServiceImpl(APIEntityRepository repository) {
        super(repository);
    }
}