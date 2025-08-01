package org.apaas.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apaas.system.domain.dto.NotificationDTO;
import org.apaas.system.entity.Notification;
import java.util.List;

/**
 * 通知服务接口
 */
public interface NotificationService extends IService<Notification> {

    /**
     * 分页查询通知列表
     *
     * @param notificationDTO 查询参数
     * @return 通知列表
     */
    List<Notification> queryPage(NotificationDTO notificationDTO);

    /**
     * 获取通知详情
     *
     * @param id 通知ID
     * @return 通知详情
     */
    Notification getDetail(Long id);

    /**
     * 创建通知
     *
     * @param notification 通知实体
     * @return 创建结果
     */
    boolean create(Notification notification);

    /**
     * 更新通知
     *
     * @param notification 通知实体
     * @return 更新结果
     */
    boolean update(Notification notification);

    /**
     * 删除通知
     *
     * @param id 通知ID
     * @return 删除结果
     */
    boolean delete(Long id);

    /**
     * 批量删除通知
     *
     * @param ids 通知ID列表
     * @return 删除结果
     */
    boolean batchDelete(List<Long> ids);

    /**
     * 标记通知为已读
     *
     * @param id 通知ID
     * @return 标记结果
     */
    boolean markAsRead(Long id);

    /**
     * 批量标记通知为已读
     *
     * @param ids 通知ID列表
     * @return 标记结果
     */
    boolean batchMarkAsRead(List<Long> ids);

    /**
     * 查询用户未读通知数量
     *
     * @param userId 用户ID
     * @return 未读通知数量
     */
    Integer countUnreadByUserId(Long userId);

    /**
     * 查询用户通知列表
     *
     * @param userId 用户ID
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 通知列表
     */
    List<Notification> getByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 发送通知
     *
     * @param notification 通知实体
     * @return 发送结果
     */
    boolean send(Notification notification);

    /**
     * 批量发送通知
     *
     * @param notifications 通知实体列表
     * @return 发送结果
     */
    boolean batchSend(List<Notification> notifications);
}