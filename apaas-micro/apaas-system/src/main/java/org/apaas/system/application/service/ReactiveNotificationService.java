package org.apaas.system.application.service;

import org.apaas.application.service.ApplicationService;
import org.apaas.system.domain.model.Notification;
import org.apaas.system.application.dto.NotificationDTO;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 响应式通知服务接口
 */
public interface ReactiveNotificationService extends ApplicationService<Notification, Long> {
    
    /**
     * 分页查询通知列表
     *
     * @param notificationDTO 查询参数
     * @return 通知列表
     */
    Flux<NotificationDTO> queryPage(NotificationDTO notificationDTO);
    
    /**
     * 获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    Mono<NotificationDTO> getDetail(Long id);
    
    Flux<NotificationDTO> getAllByIds(List<Long> ids);
    
    /**
     * 创建通知
     *
     * @param notificationDto 通知实体
     * @return 创建结果
     */
    Mono<Boolean> create(NotificationDTO notificationDto);
    
    /**
     * 更新通知
     *
     * @param notificationDto 通知实体
     * @return 更新结果
     */
    Mono<Boolean> update(NotificationDTO notificationDto);
    
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
    Flux<NotificationDTO> getByUserId(Long userId, Integer pageNum, Integer pageSize);
    
    /**
     * 发送通知
     *
     * @param notificationDto 通知实体
     * @return 发送结果
     */
    Mono<Boolean> send(NotificationDTO notificationDto);
    
    /**
     * 批量发送通知
     *
     * @param notificationDtos 通知实体列表
     * @return 发送结果
     */
    Mono<Boolean> batchSend(List<NotificationDTO> notificationDtos);
    
    /**
     * 导出通知
     *
     * @param exchange 响应对象
     * @param query    查询条件
     * @return 导出结果
     */
    Mono<Void> exportExcel(ServerWebExchange exchange, NotificationDTO query);
    
    /**
     * 导入通知
     *
     * @param fileData 文件数据
     * @return 导入结果
     */
    Mono<Boolean> importExcel(byte[] fileData);
    
    /**
     * 保存批量通知
     *
     * @param notificationDtos 通知实体列表
     * @return 保存结果
     */
    Flux<NotificationDTO> saveBatch(Flux<NotificationDTO> notificationDtos);
    
    /**
     * 更新批量通知
     *
     * @param notificationDtos 通知实体列表
     * @return 更新结果
     */
    Flux<NotificationDTO> updateBatch(Flux<NotificationDTO> notificationDtos);
}