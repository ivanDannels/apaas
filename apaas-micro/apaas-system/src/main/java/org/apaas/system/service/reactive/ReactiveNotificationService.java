package org.apaas.system.service.reactive;

import org.apaas.core.service.BaseService;
import org.apaas.system.domain.dto.NotificationDTO;
import org.apaas.system.entity.Notification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

/**
 * 响应式通知服务接口
 */
public interface ReactiveNotificationService extends BaseService<Notification, Long> {

    /**
     * 分页查询通知列表
     *
     * @param notificationDTO 查询参数
     * @return 通知列表
     */
    Flux<Notification> queryPage(NotificationDTO notificationDTO);

    /**
     * 获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    Mono<Notification> getDetail(Long id);

    Flux<Notification> getAllByIds(List<Long> ids);

    /**
     * 创建通知
     *
     * @param notification 通知实体
     * @return 创建结果
     */
    Mono<Boolean> create(Notification notification);

    /**
     * 更新通知
     *
     * @param notification 通知实体
     * @return 更新结果
     */
    Mono<Boolean> update(Notification notification);

    /**
     * 删除通知
     *
     * @param id 通知ID
     * @return 删除结果
     */
    Mono<Boolean> delete(Long id);

    /**
     * 批量删除通知
     *
     * @param ids 通知ID列表
     * @return 删除结果
     */
    Mono<Boolean> batchDelete(List<Long> ids);

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 标记结果
     */
    Mono<Boolean> markAsRead(Long id);

    /**
     * 批量标记通知为已读
     *
     * @param ids 通知ID列表
     * @return 标记结果
     */
    Mono<Boolean> batchMarkAsRead(List<Long> ids);

    /**
     * 查询用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Mono<Integer> countUnreadByUserId(Long userId);

    /**
     * 查询用户通知列表
     *
     * @param userId   用户ID
     * @param pageNum  页码
     * @param pageSize 每页数量
     * @return 通知列表
     */
    Flux<Notification> getByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 发送通知
     *
     * @param notification 通知实体
     * @return 发送结果
     */
    Mono<Boolean> send(Notification notification);

    /**
     * 批量发送通知
     *
     * @param notifications 通知实体列表
     * @return 发送结果
     */
    Mono<Boolean> batchSend(List<Notification> notifications);
}