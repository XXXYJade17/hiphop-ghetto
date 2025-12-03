package com.xxxyjade.hiphopghetto.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "UserLoginDTO", description = "用户登录DTO")
public class UserLoginDTO implements Serializable {

    @NotBlank(message = "账号不能为空")
    @Schema(name = "account",
            description = "登录账号（用户名/手机号/邮箱）")
    private String account;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度必须为8-20位")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "密码必须包含字母、数字")
    @Schema(name = "password",
            description = "密码",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
