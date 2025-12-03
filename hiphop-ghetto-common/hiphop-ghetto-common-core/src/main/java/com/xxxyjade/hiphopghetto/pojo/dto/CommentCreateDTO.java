package com.xxxyjade.hiphopghetto.pojo.dto;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建评论DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDTO {

    // 评论上级类型
    private ResourceType parentType;

    // 评论内容
    private String content;

}
