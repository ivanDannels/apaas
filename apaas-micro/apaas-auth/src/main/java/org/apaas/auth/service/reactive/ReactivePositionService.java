package org.apaas.auth.service.reactive;

import org.apaas.auth.entity.Position;
import org.apaas.domain.service.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactivePositionService extends BaseService<Position, Long> {

    /**
     * 根据用户ID获取岗位列表
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    Flux<Position> getPositionsByUserId(Long userId);

    /**
     * 为用户分配岗位
     *
     * @param userId     用户ID
     * @param positionId 岗位ID
     * @return 是否成功
     */
    Mono<Boolean> assignPositionToUser(Long userId, Long positionId);

    /**
     * 移除用户的岗位
     *
     * @param userId     用户ID
     * @param positionId 岗位ID
     * @return 是否成功
     */
    Mono<Boolean> removePositionFromUser(Long userId, Long positionId);
}