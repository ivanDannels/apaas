package org.apaas.flow.execution.repository.mapper;

import org.apaas.core.repository.BaseEntityRepository;
import org.apaas.flow.execution.domain.entity.WorkflowTask;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowTaskMapper extends BaseEntityRepository<WorkflowTask, String> {
}