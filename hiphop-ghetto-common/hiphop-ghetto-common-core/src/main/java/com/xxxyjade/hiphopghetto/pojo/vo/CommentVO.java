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
@Schema(title = "CommentVO", description = "评论VO")
public class CommentVO {

    @Schema(name = "id", description = "评论id")
    private Long id;

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "parentId", description = "评论上级id")
    private Long parentId;

    @Schema(name = "parentType", description = "评论上级类型")
    private ResourceType parentType;

    @Schema(name = "content", description = "评论内容")
    private String content;

    @Schema(name = "replyCount", description = "回复数量")
    private Integer replyCount;

    @Schema(name = "likeCount", description = "点赞数量")
    private Integer likeCount;

    @Schema(name = "createTime", description = "创建时间")
    private LocalDateTime createTime;

    @Schema(name = "isLiked", description = "是否点赞")
    private Boolean isLiked;

}
