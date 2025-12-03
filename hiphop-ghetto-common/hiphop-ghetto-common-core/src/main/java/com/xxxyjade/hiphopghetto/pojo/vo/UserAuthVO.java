package com.xxxyjade.hiphopghetto.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "UserAuthVO", description = "用户认证信息VO")
public class UserAuthVO {

    @Schema(name = "id", description = "用户Id")
    private Long id;

    @Schema(name = "token", description = "Jwt令牌")
    private String token;

}
