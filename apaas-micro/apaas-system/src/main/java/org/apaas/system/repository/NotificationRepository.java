package org.apaas.system.repository;

import org.apaas.system.entity.Notification;
import org.apaas.core.repository.ReactiveBaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 通知Repository接口
 */
@Repository
public interface NotificationRepository extends ReactiveBaseRepository<Notification, Long> {
    /**
     * 根据用户ID查询通知列表
     *
     * @param userId 用户ID
     * @return 通知列表
     */
    Flux<Notification> findByReceiverId(Long userId);

    /**
     * 根据用户ID和阅读状态查询通知列表
     *
     * @param userId 用户ID
     * @param readStatus 阅读状态
     * @return 通知列表
     */
    Flux<Notification> findByReceiverIdAndReadStatus(Long userId, Integer readStatus);

    /**
     * 根据用户ID查询未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Mono<Long> countByReceiverIdAndReadStatus(Long userId, Integer readStatus);
}