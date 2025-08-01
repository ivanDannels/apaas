package org.apaas.flow.execution.repository;

import org.apaas.core.repository.BaseRepository;
import org.apaas.flow.execution.entity.FlowInstance;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowInstanceRepository extends BaseRepository<FlowInstance, Long> {
}