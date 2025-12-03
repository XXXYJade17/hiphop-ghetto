package com.xxxyjade.hiphopghetto.pojo.vo;

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
@Schema(title = "TopicInfoVO", description = "话题详情VO")
public class TopicInfoVO {

    @Schema(name = "userId", description = "用户id")
    private Long userId;

    @Schema(name = "title", description = "话题标题")
    private String title;

    @Schema(name = "coverUrl", description = "话题封面")
    private String coverUrl;

    @Schema(name = "content", description = "话题内容")
    private String content;

    @Schema(name = "viewCount", description = "话题浏览数")
    private Integer viewCount;

    @Schema(name = "commentCount", description = "话题评论数")
    private Integer commentCount;

    @Schema(name = "likeCount", description = "话题点赞数")
    private Integer likeCount;

    @Schema(name = "createTime", description = "话题创建时间")
    private LocalDateTime createTime;

    @Schema(name = "isLiked", description = "是否点赞")
    private Boolean isLiked;

}
