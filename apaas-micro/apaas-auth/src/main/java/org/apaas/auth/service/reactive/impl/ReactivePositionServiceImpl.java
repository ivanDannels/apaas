package org.apaas.auth.service.reactive.impl;

import org.apaas.auth.entity.Position;
import org.apaas.auth.repository.reactive.ReactivePositionRepository;
import org.apaas.auth.service.reactive.ReactivePositionService;
import org.apaas.core.event.EntityChangedEvent;
import org.apaas.core.event.impl.RedisDomainEventPublisher;
import org.apaas.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactivePositionServiceImpl extends BaseServiceImpl<Position, Long, ReactivePositionRepository> implements ReactivePositionService {

    public ReactivePositionServiceImpl(ReactivePositionRepository repository, RedisDomainEventPublisher<EntityChangedEvent<Position>> eventPublisher) {
        super(repository, eventPublisher);
    }

    @Override
    public Flux<Position> getPositionsByUserId(Long userId) {
        // 实现根据用户ID获取岗位列表的逻辑
        return repository.findByIdAndDeletedFalse(userId).flux();
    }

    @Override
    public Mono<Boolean> assignPositionToUser(Long userId, Long positionId) {
        // 实现为用户分配岗位的逻辑
        return Mono.fromCallable(() -> {
            // 这里应该实现具体的业务逻辑
            // 例如在用户岗位关联表中插入一条记录
            return true;
        });
    }

    @Override
    public Mono<Boolean> removePositionFromUser(Long userId, Long positionId) {
        // 实现移除用户岗位的逻辑
        return Mono.fromCallable(() -> {
            // 这里应该实现具体的业务逻辑
            // 例如从用户岗位关联表中删除一条记录
            return true;
        });
    }
}