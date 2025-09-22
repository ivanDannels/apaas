package org.apaas.auth.domain.dto;

import lombok.Data;

/**
 * 角色查询DTO
 */
@Data
public class RoleDTO {
    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;
}