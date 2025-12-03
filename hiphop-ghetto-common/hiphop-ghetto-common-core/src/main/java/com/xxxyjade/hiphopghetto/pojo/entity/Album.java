package com.xxxyjade.hiphopghetto.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 专辑实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("album")
public class Album implements Serializable {

    /**
     * 专辑id
     */
    private Long id;

    /**
     * 专辑名
     */
    private String albumName;

    /**
     * 歌手名
     */
    private String artists;

    /**
     * 发行时间
     */
    private LocalDate releaseTime;

    /**
     * 封面
     */
    private String coverUrl;

    /**
     * 简介
     */
    private String description;

    /**
     * 平均评分(100分制)
     */
    private BigDecimal avgScore;

    /**
     * 累计评分
     */
    @Builder.Default
    private Integer ratingCount = 0;

    /**
     * 累计收藏
     */
    @Builder.Default
    private Integer collectionCount = 0;

    /**
     * 累计评论
     */
    @Builder.Default
    private Integer commentCount = 0;

}
