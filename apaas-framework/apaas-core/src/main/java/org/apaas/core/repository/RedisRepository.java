package org.apaas.core.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

/**
 * @author ivan
 */
@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public Mono<Boolean> set(String key, Object value, long timeout) {
        return redisTemplate.opsForValue().set(key, value, Duration.ofMillis(timeout));
    }

    public Mono<Boolean> setIfAbsent(String key, Object value, long timeout) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofMillis(timeout));
    }

    public Mono<Object> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> delete(String key) {
        return redisTemplate.delete(key).map(count -> count > 0);
    }

    public Mono<Boolean> hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Mono<Long> getExpire(String key) {
        return redisTemplate.getExpire(key).map(duration -> duration.toMillis());
    }

    public Mono<Boolean> expire(String key, long timeout) {
        return redisTemplate.expire(key, Duration.ofMillis(timeout));
    }

    public Flux<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}