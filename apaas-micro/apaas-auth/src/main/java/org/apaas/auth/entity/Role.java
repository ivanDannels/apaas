package org.apaas.authorization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.apaas.auth.domain.DataScope;

/**
 * 角色实体类
 */
@Data
@TableName("sys_role")
public class Role {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：0-启用，1-禁用
     */
    private Integer status;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志：0-未删除，1-已删除
     */
    private Integer deleted;

    /**
     * 数据范围类型
     */
    @TableField("data_scope_type")
    private String dataScopeType;

    /**
     * 自定义部门ID列表
     */
    @TableField(exist = false)
    private List<Long> customDeptIds;

    /**
     * 获取数据范围枚举
     */
    public DataScope getDataScope() {
        return DataScope.getByCode(this.dataScopeType);
    }

    /**
     * 设置数据范围枚举
     */
    public void setDataScope(DataScope dataScope) {
        this.dataScopeType = dataScope != null ? dataScope.getCode() : null;
    }
}