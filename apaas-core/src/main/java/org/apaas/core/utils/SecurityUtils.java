package org.apaas.core.utils;

import org.apaas.api.common.UserDto;

/**
 * @author ivan
 */
public class SecurityUtils {
    public static String getUsername() {
        return "admin";
    }

    public static Long getTenantId() {
        return 1L;
    }

    public static UserDto getCurrentUser() {
        return UserDto.builder()
                .username("admin")
                .password("admin")
                .tenantId(1L)
                .tenantName("apaas")
                .tenantCode("apaas")
                .organizationId(1L)
                .build();
    }
}
