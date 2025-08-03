package org.apaas.system.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 用户实体类
 * @author ivan
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Table("sys_user")
public class SysUser extends BaseEntity {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户状态（0-正常，1-停用）
     */
    private Integer status;

    /**
     * 是否是管理员（0-否，1-是）
     */
    private int isAdmin;

}