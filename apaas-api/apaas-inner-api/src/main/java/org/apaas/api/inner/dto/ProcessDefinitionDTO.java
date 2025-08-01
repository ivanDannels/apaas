package org.apaas.api.inner.dto;

import lombok.Data;

import java.util.Date;

/**
 * 流程定义DTO
 */
@Data
public class ProcessDefinitionDTO {
    /**
     * 流程定义ID
     */
    private String id;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程编码
     */
    private String code;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 状态：0-禁用，1-激活
     */
    private Integer status;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 流程XML内容
     */
    private String xmlContent;

    /**
     * 关联表单ID
     */
    private String formId;

    /**
     * 租户ID
     */
    private String tenantId;
}