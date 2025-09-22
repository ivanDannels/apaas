package org.apaas.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.apaas.core.domain.BaseEntity;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


/**
 * 操作日志
 * @author ivan
 */
@Data
@SuperBuilder
@Table("sys_oper_log")
@EqualsAndHashCode(callSuper = true)
public class SysOperLog extends BaseEntity {
    
    private String title;
    
    private Integer businessType;
    
    private String method;
    
    private String requestMethod;
    
    private String operatorType;
    
    private String operName;
    
    private String deptName;
    
    private String operUrl;
    
    private String operIp;
    
    private String operLocation;
    
    private String operParam;
    
    private String jsonResult;
    
    private Integer status;
    
    private String errorMsg;
    
    private LocalDateTime operTime;

}