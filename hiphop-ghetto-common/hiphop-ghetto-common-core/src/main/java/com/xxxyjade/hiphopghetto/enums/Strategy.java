package com.xxxyjade.hiphopghetto.enums;

public enum Strategy {
//==================== Stats ====================
    COLLECTION_COUNT,
    RATING_COUNT,
    COMMENT_COUNT,
    LIKE_COUNT,
    SUBSCRIPTION_COUNT,

//==================== Message ====================
    CRAWL_ALBUM;

    @Override
    public String toString() {
        String[] parts = this.name().split("_");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            result.append(Character.toUpperCase(parts[i].charAt(0)))
                    .append(parts[i].substring(1).toLowerCase());
        }
        return result.toString();
    }
}
