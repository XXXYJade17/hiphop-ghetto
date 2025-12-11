package com.xxxyjade.hiphopghetto.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommentSortType {
    DEFAULT("id"), // 默认
    LIKE_COUNT("like_count"), // 最高评分
    CREATE_TIME("create_time"); // 最新发布

    private final String field;

    @Override
    public String toString() {
        return this.field;
    }
}
