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
package org.apaas.design.platform.service.reactive.impl;

import org.apaas.design.platform.entity.Model;
import org.apaas.design.platform.repository.ModelRepository;
import org.apaas.design.platform.service.reactive.ReactiveModelService;
import org.apaas.domain.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 响应式模型服务实现类
 */
@Service
public class ReactiveModelServiceImpl extends BaseServiceImpl<Model, Long, ModelRepository> implements ReactiveModelService {
    
    public ReactiveModelServiceImpl(ModelRepository repository) {
        super(repository);
    }
    
}