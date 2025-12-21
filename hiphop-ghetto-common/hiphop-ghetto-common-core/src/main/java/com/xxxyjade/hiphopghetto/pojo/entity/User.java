package com.xxxyjade.hiphopghetto.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {

    /**
     * id
     * 雪花生成
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 性别
     * 0 - 未知
     * 1 - 男
     * 2 - 女
     */
    private Integer sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 背景图
     */
    private String background;

    /**
     * 简介
     */
    private String description;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 关注数
     */
    private Integer subscriptionCount;

    /**
     * 粉丝数
     */
    private Integer fanCount;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 数据状态
     */
    @TableLogic(value = "1", delval = "0")
    private Integer status;

}
