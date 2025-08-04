package org.apaas.auth.domain.dto;

import lombok.Data;

/**
 * 用户查询DTO
 */
@Data
public class UserDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Long deptId;
}