package com.xxxyjade.hiphopghetto.pojo.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "SongDetailVO", description = "歌曲详情VO")
public class SongDetailVO {

    @Schema(name = "songName", description = "歌曲名")
    private String songName;

    @Schema(name = "albumId", description = "所属专辑Id")
    private Long albumId;

    @Schema(name = "albumName", description = "所属专辑名")
    private String albumName;

    @Schema(name = "artists", description = "歌手名")
    private String artists;

    @Schema(name = "releaseTime", description = "发行时间")
    private LocalDate releaseTime;

    @Schema(name = "duration", description = "时长（秒）")
    private Integer duration;

    @Schema(name = "coverUrl", description = "封面URL")
    private String coverUrl;

    @Schema(name = "avgScore", description = "综合评分")
    private BigDecimal avgScore;

    @Schema(name = "scoreCount", description = "评分总数")
    private Integer scoreCount;

    @Schema(name = "collectionCount", description = "收藏总数")
    private Integer collectionCount;

    @Schema(name = "commentCount", description = "评论总数")
    private Integer commentCount;

}
