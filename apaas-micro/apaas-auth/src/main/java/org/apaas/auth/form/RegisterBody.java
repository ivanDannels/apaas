package org.apaas.auth.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户注册对象
 */
@Data
@Schema(description = "用户注册对象")
public class RegisterBody extends LoginBody {

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phonenumber;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;
}