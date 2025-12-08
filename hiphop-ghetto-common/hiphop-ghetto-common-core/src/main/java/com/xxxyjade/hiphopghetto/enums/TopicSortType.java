package com.xxxyjade.hiphopghetto.enums;

import lombok.Getter;

@Getter
public enum TopicSortType {
    DEFAULT("id"), // 默认
    VIEW_COUNT("view_count"), // 最多浏览
    COMMENT_COUNT("comment_count"), // 最多评分
    LIKE_COUNT("like_count"), // 最多点赞
    CREATE_TIME("create_time"); // 最新发布

    private final String type;

    TopicSortType(String type) {
        this.type = type;
    }
}
