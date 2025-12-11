package com.xxxyjade.hiphopghetto.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.xxxyjade.hiphopghetto.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment {

    /**
     * 评论id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 评论上级id
     */
    private Long parentId;

    /**
     * 评论上级类型
     */
    private ResourceType parentType;

    /**
     * 评论根id
     */
    private Long rootId;

    /**
     * 评论根类型
     */
    private ResourceType rootType;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 数据状态
     */
    @TableLogic(value = "1", delval = "0")
    private Integer status;

}
