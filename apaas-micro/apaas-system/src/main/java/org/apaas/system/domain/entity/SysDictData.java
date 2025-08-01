package org.apaas.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apaas.core.domain.BaseEntity;

/**
 * 字典数据实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
@Schema(description = "字典数据实体")
public class SysDictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典排序
     */
    @Schema(description = "字典排序")
    private Integer dictSort;

    /**
     * 字典标签
     */
    @Schema(description = "字典标签")
    private String dictLabel;

    /**
     * 字典键值
     */
    @Schema(description = "字典键值")
    private String dictValue;

    /**
     * 字典类型
     */
    @Schema(description = "字典类型")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    @Schema(description = "样式属性")
    private String cssClass;

    /**
     * 表格回显样式
     */
    @Schema(description = "表格回显样式")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @Schema(description = "是否默认（Y是 N否）")
    private String isDefault;

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