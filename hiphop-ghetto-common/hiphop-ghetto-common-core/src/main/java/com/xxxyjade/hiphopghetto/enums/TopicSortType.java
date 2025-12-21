package com.xxxyjade.hiphopghetto.enums;

import lombok.Getter;

@Getter
public enum TopicSortType {
    VIEW_COUNT("view_count"), // 最多浏览
    COMMENT_COUNT("comment_count"), // 最多点赞
    LIKE_COUNT("like_count"), // 最多点赞
    CREATE_TIME("create_time"); // 最新发布

    private final String field;

    TopicSortType(String field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return field;
    }

}
