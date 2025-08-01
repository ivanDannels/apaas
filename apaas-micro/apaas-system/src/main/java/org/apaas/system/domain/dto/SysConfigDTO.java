package org.apaas.system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apaas.core.web.domain.BasePageQuery;

/**
 * 参数配置DTO
 */
@Data
@Schema(description = "参数配置DTO")
public class SysConfigDTO extends BasePageQuery {
    /**
     * 参数ID
     */
    @Schema(description = "参数ID")
    private Long id;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String name;

    /**
     * 参数键
     */
    @Schema(description = "参数键")
    private String configKey;

    /**
     * 参数编码
     */
    @Schema(description = "参数编码")
    private String code;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    private String value;

    /**
     * 参数类型：0-系统参数，1-业务参数
     */
    @Schema(description = "参数类型：0-系统参数，1-业务参数")
    private Integer type;

    /**
     * 状态：0-正常，1-停用
     */
    @Schema(description = "状态：0-正常，1-停用")
    private Integer status;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;
}