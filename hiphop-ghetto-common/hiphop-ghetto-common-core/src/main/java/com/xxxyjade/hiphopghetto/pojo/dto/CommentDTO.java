package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    // 评论id
    private Long id;

    // 用户id
    private Long userId;

    // 评论上级id
    private Long parentId;

    // 评论上级类型
    private ResourceType parentType;

    // 评论内容
    private String content;

}
