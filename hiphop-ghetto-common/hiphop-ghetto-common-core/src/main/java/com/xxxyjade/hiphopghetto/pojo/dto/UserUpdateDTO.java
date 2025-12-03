package com.xxxyjade.hiphopghetto.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@Schema(title = "UserUpdateDTO", description = "用户更新DTO")
public class UserUpdateDTO {

    @NotNull
    @Schema(name = "id", description = "用户Id")
    private Long id;

    @Schema(name = "username", description = "用户名")
    private String username;

    @Schema(name = "phone", description = "手机号")
    private String phone;

    @Schema(name = "email", description = "邮箱")
    private String email;

    @Schema(name = "password", description = "密码")
    private String password;

    @Schema(name = "nickname", description = "昵称")
    private String nickname;

    @Schema(name = "sex", description = "性别")
    private Integer sex;

    @Schema(name = "avatar", description = "头像")
    private String avatar;

    @Schema(name = "background", description = "背景图")
    private String background;

    @Schema(name = "description", description = "简介")
    private String description;

    @Schema(name = "birthday", description = "生日")
    private LocalDate birthday;

}
