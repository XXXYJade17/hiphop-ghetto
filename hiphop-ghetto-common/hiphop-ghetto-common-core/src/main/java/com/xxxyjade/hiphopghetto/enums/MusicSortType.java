package com.xxxyjade.hiphopghetto.enums;

import lombok.Getter;

@Getter
public enum MusicSortType {
    DEFAULT("_id"),
    AVG_SCORE("stats.avgScore"), // 最高评分
    COLLECT_COUNT("stats.collectionCount"), // 最多收藏
    COMMENT_COUNT("stats.commentCount"), // 最多评论
    RELEASE_TIME("publishTime"); // 最新发行

    private final String field;

    MusicSortType(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return field;
    }

}
