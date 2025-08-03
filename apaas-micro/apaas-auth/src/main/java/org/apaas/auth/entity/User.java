package org.apaas.auth.entity;

import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别：0-男，1-女
     */
    private Integer gender;

    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;

}