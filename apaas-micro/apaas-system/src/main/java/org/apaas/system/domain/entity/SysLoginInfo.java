package org.apaas.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * 系统访问记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_login_info")
@Schema(description = "系统访问记录实体")
public class SysLoginInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    private String userName;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    private String ipaddr;

    /**
     * 登录地点
     */
    @Schema(description = "登录地点")
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @Schema(description = "浏览器类型")
    private String browser;

    /**
     * 操作系统
     */
    @Schema(description = "操作系统")
    private String os;

    /**
     * 登录状态（0成功 1失败）
     */
    @Schema(description = "登录状态")
    private String status;

    /**
     * 提示消息
     */
    @Schema(description = "提示消息")
    private String msg;

    /**
     * 访问时间
     */
    @Schema(description = "访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;
}