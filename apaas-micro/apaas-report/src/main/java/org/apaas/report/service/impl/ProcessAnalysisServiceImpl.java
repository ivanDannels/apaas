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
package org.apaas.report.service.impl;

import org.apaas.application.service.AbstractApplicationService;
import org.apaas.report.entity.ProcessAnalysis;
import org.apaas.report.repository.ProcessAnalysisRepository;
import org.apaas.report.service.ProcessAnalysisService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ivan
 */
@Service
public class ProcessAnalysisServiceImpl extends AbstractApplicationService<ProcessAnalysis, Long, ProcessAnalysisRepository> implements ProcessAnalysisService {
    
    public ProcessAnalysisServiceImpl(ProcessAnalysisRepository repository) {
        super(repository);
    }
    
    @Override
    public Mono<ProcessAnalysis> findByProcessName(String processName) {
        return domainService.findByProcessName(processName);
    }
    
    @Override
    public Flux<ProcessAnalysis> findByProcessId(Long processId) {
        return domainService.findByProcessId(processId);
    }
}