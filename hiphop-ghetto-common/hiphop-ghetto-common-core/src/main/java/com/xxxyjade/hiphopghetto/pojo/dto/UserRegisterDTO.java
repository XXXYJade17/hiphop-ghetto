package com.xxxyjade.hiphopghetto.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Schema(title = "UserRegisterDTO", description = "用户注册DTO")
public class UserRegisterDTO implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 6,max = 16, message = "用户名长度长度必须为6-16位")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", message = "用户名只能包含中文、字母、数字和下划线")
    @Schema(name = "username",
            description = "用户名",
            requiredMode = Schema.RequiredMode.REQUIRED,
            defaultValue = "administrator")
    private String username;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    @Schema(name = "phone",
            description = "手机号（选填，与邮箱至少填一个）",
            defaultValue = "17276090504")
    private String phone;

    @Email(message = "邮箱格式不正确")
    @Schema(name = "email",
            description = "邮箱（选填，与手机号至少填一个）",
            defaultValue = "admin@example.com")
    private String email;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 20, message = "密码长度必须为8-20位")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "密码必须包含字母、数字")
    @Schema(name = "password",
            description = "密码",
            requiredMode = Schema.RequiredMode.REQUIRED,
            defaultValue = "a1234567")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @Schema(name = "confirmPassword",
            description = "确认密码（需与密码一致）",
            requiredMode = Schema.RequiredMode.REQUIRED,
            defaultValue = "a1234567")
    private String confirmPassword;

}
