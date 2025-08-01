package org.apaas.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 通知DTO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationDTO extends BasePageQuery {

    @Schema(description = "通知ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "接收人ID")
    private Long receiverId;

    @Schema(description = "接收人名称")
    private String receiverName;

    @Schema(description = "发送人ID")
    private Long senderId;

    @Schema(description = "发送人名称")
    private String senderName;

    @Schema(description = "通知类型")
    private String type;

    @Schema(description = "通知渠道")
    private String channel;

    @Schema(description = "关联业务ID")
    private String businessId;

    @Schema(description = "关联业务类型")
    private String businessType;

    @Schema(description = "阅读状态 0:未读 1:已读")
    private Integer readStatus;

    @Schema(description = "阅读时间")
    private LocalDateTime readTime;

    @Schema(description = "发送状态 0:未发送 1:已发送 2:发送失败")
    private Integer sendStatus;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "模板ID")
    private Long templateId;

    @Schema(description = "租户ID")
    private Long tenantId;
}