package org.apaas.authorization.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 资源实体类
 */
@Data
@Table("sys_resource")
public class Resource {
    /**
     * 资源ID
     */
    @Id
    @Column("id")
    private Long id;

    /**
     * 资源名称
     */
    @Column("name")
    private String name;

    /**
     * 资源类型：0-菜单，1-按钮
     */
    @Column("type")
    private Integer type;

    /**
     * 路径
     */
    @Column("path")
    private String path;

    /**
     * 组件
     */
    @Column("component")
    private String component;

    /**
     * 权限标识
     */
    @Column("permission")
    private String permission;

    /**
     * 父级ID
     */
    @Column("parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @Column("sort")
    private Integer sort;

    /**
     * 图标
     */
    @Column("icon")
    private String icon;

    /**
     * 状态：0-启用，1-禁用
     */
    @Column("status")
    private Integer status;

    /**
     * 租户ID
     */
    @Column("tenant_id")
    private Long tenantId;

    /**
     * 创建人
     */
    @Column("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @Column("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column("update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志：0-未删除，1-已删除
     */
    @Column("deleted")
    private Integer deleted;

    /**
     * 子资源列表（非数据库字段）
     */
    @Transient
    private List<Resource> children;

    /**
     * 是否选中（用于角色资源分配，非数据库字段）
     */
    @Transient
    private Boolean selected;
}