package org.apaas.flow.engine.repository.reactive;

import org.apaas.core.repository.ReactiveBaseRepository;
import org.apaas.flow.engine.entity.FlowTask;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ReactiveFlowTaskRepository extends ReactiveBaseRepository<FlowTask, Long> {
    Mono<Void> deleteByInstanceId(Long id);

    Mono<Boolean> deleteAllByCandidateIdsIn(List<Long> ids);
}