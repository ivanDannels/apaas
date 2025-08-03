package org.apaas.flow.execution.repository;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTaskRepository extends ReactiveBaseRepository<WorkflowTask, Long> {
}