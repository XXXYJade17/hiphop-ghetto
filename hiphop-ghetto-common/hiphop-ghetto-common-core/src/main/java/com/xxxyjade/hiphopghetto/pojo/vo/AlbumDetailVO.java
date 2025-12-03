package com.xxxyjade.hiphopghetto.pojo.vo;

import com.xxxyjade.hiphopghetto.pojo.entity.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "AlbumDetailVO", description = "专辑详情VO")
public class AlbumDetailVO implements Serializable {

    @Schema(name = "albumName", description = "专辑名")
    private String albumName;

    @Schema(name = "artists", description = "歌手名")
    private String artists;

    @Schema(name = "releaseTime", description = "发行时间")
    private LocalDate releaseTime;

    @Schema(name = "coverUrl", description = "封面")
    private String coverUrl;

    @Schema(name = "description", description = "简介")
    private String description;

    @Schema(name = "avgScore", description = "综合评分")
    private BigDecimal avgScore;

    @Schema(name = "ratingCount", description = "评分总数")
    private Integer ratingCount;

    @Schema(name = "collectionCount", description = "收藏总数")
    private Integer collectionCount;

    @Schema(name = "commentCount", description = "评论总数")
    private Integer commentCount;

    @Schema(name = "songs", description = "专辑歌曲")
    private List<Song> songs;

}
