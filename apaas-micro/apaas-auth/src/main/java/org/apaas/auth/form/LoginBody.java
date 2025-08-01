package org.apaas.auth.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户登录对象
 */
@Data
@Schema(description = "用户登录对象")
public class LoginBody {

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 用户密码
     */
    @Schema(description = "用户密码")
    private String password;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    private String code;

    /**
     * 唯一标识
     */
    @Schema(description = "唯一标识")
    private String uuid;
}