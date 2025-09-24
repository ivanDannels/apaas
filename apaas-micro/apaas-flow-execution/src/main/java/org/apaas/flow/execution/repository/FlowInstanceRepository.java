package org.apaas.flow.execution.repository;

import org.apaas.domain.repository.ReactiveBaseRepository;
import org.apaas.flow.execution.domain.entity.FlowInstance;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowInstanceRepository extends ReactiveBaseRepository<FlowInstance, Long> {
}