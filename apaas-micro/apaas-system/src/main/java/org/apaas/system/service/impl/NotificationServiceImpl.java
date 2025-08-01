package org.apaas.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apaas.system.common.util.SecurityUtils;
import org.apaas.system.domain.dto.NotificationDTO;
import org.apaas.system.entity.Notification;
import org.apaas.system.mapper.NotificationMapper;
import org.apaas.system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知服务实现类
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Override
    public List<Notification> queryPage(NotificationDTO notificationDTO) {
        Page<Notification> page = new Page<>(notificationDTO.getPageNum(), notificationDTO.getPageSize());
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();

        // 设置查询条件
        if (notificationDTO.getTitle() != null && !notificationDTO.getTitle().isEmpty()) {
            queryWrapper.like("title", notificationDTO.getTitle());
        }
        if (notificationDTO.getType() != null && !notificationDTO.getType().isEmpty()) {
            queryWrapper.eq("type", notificationDTO.getType());
        }
        if (notificationDTO.getReadStatus() != null) {
            queryWrapper.eq("read_status", notificationDTO.getReadStatus());
        }
        if (notificationDTO.getReceiverId() != null) {
            queryWrapper.eq("receiver_id", notificationDTO.getReceiverId());
        }
        if (notificationDTO.getSenderId() != null) {
            queryWrapper.eq("sender_id", notificationDTO.getSenderId());
        }

        // 设置排序
        queryWrapper.orderByDesc("create_time");

        // 执行分页查询
        IPage<Notification> result = notificationMapper.selectPage(page, queryWrapper);
        return result.getRecords();
    }

    @Override
    public Notification getDetail(Long id) {
        return notificationMapper.selectById(id);
    }

    @Override
    @Transactional
    public boolean create(Notification notification) {
        // 设置创建人、创建时间等信息
        Long userId = SecurityUtils.getUserId();
        notification.setCreateBy(userId);
        notification.setCreateTime(LocalDateTime.now());
        notification.setUpdateBy(userId);
        notification.setUpdateTime(LocalDateTime.now());
        notification.setTenantId(SecurityUtils.getTenantId());

        // 默认未读、未发送
        notification.setReadStatus(0);
        notification.setSendStatus(0);

        return save(notification);
    }

    @Override
    @Transactional
    public boolean update(Notification notification) {
        // 设置更新人、更新时间等信息
        notification.setUpdateBy(SecurityUtils.getUserId());
        notification.setUpdateTime(LocalDateTime.now());

        return updateById(notification);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return removeById(id);
    }

    @Override
    @Transactional
    public boolean batchDelete(List<Long> ids) {
        return removeByIds(ids);
    }

    @Override
    @Transactional
    public boolean markAsRead(Long id) {
        Notification notification = new Notification();
        notification.setId(id);
        notification.setReadStatus(1);
        notification.setReadTime(LocalDateTime.now());
        notification.setUpdateBy(SecurityUtils.getUserId());
        notification.setUpdateTime(LocalDateTime.now());

        return updateById(notification);
    }

    @Override
    @Transactional
    public boolean batchMarkAsRead(List<Long> ids) {
        return notificationMapper.batchUpdateReadStatus(ids, 1) > 0;
    }

    @Override
    public Integer countUnreadByUserId(Long userId) {
        return notificationMapper.countUnreadByUserId(userId);
    }

    @Override
    public List<Notification> getByUserId(Long userId, Integer pageNum, Integer pageSize) {
        return notificationMapper.selectByUserId(userId, pageNum, pageSize);
    }

    @Override
    @Transactional
    public boolean send(Notification notification) {
        // 更新发送状态
        notification.setSendStatus(1);
        notification.setSendTime(LocalDateTime.now());
        notification.setUpdateBy(SecurityUtils.getUserId());
        notification.setUpdateTime(LocalDateTime.now());

        // 实际发送逻辑可以在这里实现，例如发送邮件、短信等
        // TODO: 实现具体的发送逻辑

        return updateById(notification);
    }

    @Override
    @Transactional
    public boolean batchSend(List<Notification> notifications) {
        boolean result = true;
        for (Notification notification : notifications) {
            boolean sendResult = send(notification);
            if (!sendResult) {
                result = false;
                // 记录失败原因
                notification.setSendStatus(2);
                notification.setFailReason("发送失败");
                updateById(notification);
            }
        }
        return result;
    }
}