package com.xxxyjade.hiphopghetto.pojo.vo;

import com.xxxyjade.hiphopghetto.enums.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {

    private Long id;
    private Long userId;
    private Long parentId;
    private ResourceType parentType;
    private String content;
    private Integer replyCount;
    private Integer likeCount;
    private LocalDateTime createTime;
    private Boolean isLiked;

}
