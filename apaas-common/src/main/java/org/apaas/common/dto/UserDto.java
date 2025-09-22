package org.apaas.common.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author ivan
 */
@Data
@Builder
public class UserDto {

    private Long userId;
    private String username;
    private String password;
    private Long tenantId;
    private String tenantName;
    private String tenantCode;
    // 组织
    private Long organizationId;

}
