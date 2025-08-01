package org.apaas.flow.execution.repository.mapper;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.flow.execution.entity.FlowInstance;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowInstanceMapper extends BaseEntityRepository<FlowInstance, String> {
}