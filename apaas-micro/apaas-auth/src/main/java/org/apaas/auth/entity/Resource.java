package org.apaas.auth.entity;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import lombok.Data;

/**
 * 资源实体类
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_resource")
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity {

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源类型：0-菜单，1-按钮
     */
    private Integer type;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 父级ID
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer sequence;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;

    /**
     * 应用ID
     */
    private Long applicationId;

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