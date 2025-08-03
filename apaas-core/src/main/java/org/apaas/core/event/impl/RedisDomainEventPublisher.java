package org.apaas.core.event.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apaas.core.event.DomainEventPublisher;
import org.apaas.core.repository.RedisRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 基于Redis的领域事件发布器实现
 * @param <T> 事件类型
 */
@Component
@RequiredArgsConstructor
public class RedisDomainEventPublisher<T> implements DomainEventPublisher<T> {
    
    private final RedisRepository redisRepository;
    
    private final ObjectMapper objectMapper;
    
    private String eventChannel;
    
    @Override
    public Mono<Void> publish(T event) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(event))
                .flatMap(json -> redisRepository.set(eventChannel + ":" + System.currentTimeMillis(), json, 60000))
                .then();
    }
}