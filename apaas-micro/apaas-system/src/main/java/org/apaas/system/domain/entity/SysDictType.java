package org.apaas.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

/**
 * 字典类型实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
@Schema(description = "字典类型实体")
public class SysDictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

    /**
     * 状态（0：禁用，1：启用）
     */
    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}