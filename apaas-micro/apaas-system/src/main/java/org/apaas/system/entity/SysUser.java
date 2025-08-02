package org.apaas.system.entity;


import lombok.Data;
import org.apaas.core.domain.BaseEntity;

/**
 * 用户实体类
 */
@Data
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
}