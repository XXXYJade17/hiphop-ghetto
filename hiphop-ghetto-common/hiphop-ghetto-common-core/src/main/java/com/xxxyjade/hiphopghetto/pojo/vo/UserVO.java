package com.xxxyjade.hiphopghetto.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UserVO", description = "用户VO")
public class UserVO implements Serializable {

    /**
     * 用户名
     */
    @Schema(name = "nickname", description = "用户名")
    private String nickname;

    /**
     * 性别
     * 0 - 未知
     * 1 - 男
     * 2 - 女
     */
    @Schema(name = "sex", description = "性别：0-未知 1-男 2-女")
    private Integer sex;

    /**
     * 头像
     */
    @Schema(name = "avatar", description = "头像")
    private String avatar;

    /**
     * 简介
     */
    @Schema(name = "description", description = "简介")
    private String description;

    /**
     * 生日
     */
    @Schema(name = "birthday", description = "生日")
    private LocalDate birthday;

}
