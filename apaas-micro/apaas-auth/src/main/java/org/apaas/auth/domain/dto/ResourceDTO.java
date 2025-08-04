package org.apaas.auth.domain.dto;

import lombok.Data;

/**
 * 资源查询DTO
 */
@Data
public class ResourceDTO {

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型：0-菜单，1-按钮
     */
    private Integer type;

    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 租户ID
     */
    private Long tenantId;

}