package org.apaas.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 通知实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notification")
public class Notification extends BaseEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 接收人ID
     */
    private Long receiverId;

    /**
     * 接收人名称
     */
    private String receiverName;

    /**
     * 发送人ID
     */
    private Long senderId;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 通知类型
     */
    private String type;

    /**
     * 通知渠道
     */
    private String channel;

    /**
     * 关联业务ID
     */
    private String businessId;

    /**
     * 关联业务类型
     */
    private String businessType;

    /**
     * 阅读状态 0:未读 1:已读
     */
    private Integer readStatus;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

    /**
     * 发送状态 0:未发送 1:已发送 2:发送失败
     */
    private Integer sendStatus;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 失败原因
     */
    private String failReason;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;
}