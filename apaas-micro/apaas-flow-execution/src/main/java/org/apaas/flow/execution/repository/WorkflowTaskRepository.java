package org.apaas.flow.execution.repository;

import org.apaas.core.repository.BaseRepository;
import org.apaas.flow.execution.entity.WorkflowTask;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTaskRepository extends BaseRepository<WorkflowTask, Long> {
}