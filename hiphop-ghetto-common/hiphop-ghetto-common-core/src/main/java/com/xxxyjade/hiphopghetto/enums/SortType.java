package com.xxxyjade.hiphopghetto.enums;

import lombok.Getter;

@Getter
public enum SortType {
    DEFAULT("default"), // 默认
    AVG_SCORE("avg_score"), // 最高评分
    COLLECT_COUNT("collect_count"), // 最多收藏
    COMMENT_COUNT("comment_count"), // 最多评论
    RELEASE_TIME("release_time"), // 最新发布
    CREATE_TIME("create_time"); // 最新发布

    private String type;

    SortType(String type) {
        this.type = type;
    }

}
