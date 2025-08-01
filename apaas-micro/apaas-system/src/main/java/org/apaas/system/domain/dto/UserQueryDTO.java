package org.apaas.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * 用户查询DTO
 */
@Data
@Schema(description = "用户查询DTO")
public class UserQueryDTO extends BasePageQuery {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 状态（0：禁用，1：启用）
     */
    @Schema(description = "状态（0：禁用，1：启用）")
    private Integer status;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private String beginTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private String endTime;
}